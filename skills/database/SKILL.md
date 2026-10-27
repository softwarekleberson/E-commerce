---
name: clean-ecommerce-database
description: Explain and safely modify the Clean Ecommerce MySQL database schema, including table attributes, constraints, relationships, and updates. Excludes data insertion scripts.
metadata:
  short-description: Understand and update the ecommerce database
---

# Banco de dados do Clean Ecommerce

Use esta skill para entender o esquema MySQL, planejar alterações de estrutura e atualizar dados existentes neste projeto. A fonte primária do esquema é `src/main/resources/db/migration/`; confirme também as entidades JPA, mappers, gateways e repositórios antes de alterar persistência. O projeto usa Flyway: migrations já aplicadas não devem ser editadas; crie uma nova migration versionada.

## Escopo e fontes

- Considere somente migrations de estrutura (CREATE, ALTER, índices, chaves e constraints). Ignore migrations e arquivos cuja finalidade seja inserir dados; não reproduza nem recomende executar inserts de seed.
- Leia as migrations na ordem de versão, pois alterações posteriores modificam o esquema original. Em particular, `V22` remove `value` de `tb_description_payment`, `V23` liga pagamentos a pedidos e `V34` adiciona `product_ref_id` a itens do pedido.
- Confira as entidades em `src/main/java/com/cleancode/ecommerce/**/infra/persistence/` (alguns módulos usam nomes de pasta diferentes), mappers e configuração Flyway em `src/main/resources/application.properties`.
- O banco é MySQL. Tipos, `ENUM`, `AUTO_INCREMENT`, `ON UPDATE CURRENT_TIMESTAMP`, índices e semântica de `ON DELETE` devem ser considerados conforme MySQL.

## Esquema atual

`PK` = chave primária; `FK` = chave estrangeira. Salvo indicação diferente, identificadores UUID são armazenados como `VARCHAR(36)`. `NOT NULL` indica obrigatório; colunas sem essa restrição aceitam nulo. Os nomes e tipos abaixo refletem as migrations, inclusive inconsistências existentes.

### Clientes e endereços

#### `tb_customer` — cliente e dados de autenticação

- `customer_id VARCHAR(36)` PK: identificador do cliente.
- `system_client_status BOOLEAN DEFAULT TRUE`: situação de cliente do sistema.
- `cpf VARCHAR(15) NOT NULL`: CPF com pontuação; índice `idx_tb_customer_cpf`.
- `full_name VARCHAR(150) NOT NULL`: nome; índice FULLTEXT `idx_tb_customer_full_name`.
- `birth_date DATE NOT NULL`: nascimento.
- `password_hash VARCHAR(512) NOT NULL`: hash da senha, nunca senha em texto simples.
- `gender ENUM('MALE','FEMALE','NOT_INFORMED') NOT NULL`.
- `area_code CHAR(2) NOT NULL`, `phone_number VARCHAR(9) NOT NULL`, `phone_type ENUM('MOBILE','WHATSAPP','LANDLINE') NOT NULL`: telefone.
- `email VARCHAR(255) UNIQUE NOT NULL`: e-mail; índice `idx_tb_customer_email` (há redundância entre UNIQUE e índice explícito).
- `created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP`; `updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP`.
- Relações: possui endereços (`tb_address`), cartões (`tb_card`), carrinhos (`tb_cart`) e reservas (`tb_reservation`), com exclusão em cascata nessas FKs. `tb_replacement.customer_id`, `tb_payment.customer_id` e `tb_voucher.customer_id` não têm FK declarada.

#### `tb_address` — endereço

- `address_id BIGINT AUTO_INCREMENT` PK; `public_id VARCHAR(36) UNIQUE NOT NULL` identificador público.
- `main BOOLEAN NOT NULL DEFAULT FALSE`, `receiver VARCHAR(255) NOT NULL`.
- `street VARCHAR(255) NOT NULL`, `number VARCHAR(50) NOT NULL`, `neighborhood VARCHAR(255) NOT NULL`, `zip_code CHAR(9) NOT NULL`, `observation VARCHAR(255)` opcional.
- `street_type VARCHAR(255) NOT NULL`, `residence_type VARCHAR(255) NOT NULL`, `city VARCHAR(150) NOT NULL`, `state VARCHAR(150) NOT NULL`, `country VARCHAR(150) NOT NULL`.
- `customer_id VARCHAR(36)` opcional FK para `tb_customer.customer_id`, `ON DELETE CASCADE`; índice em `public_id`.
- Relações: endereço pode estar associado a cobranças e entregas.

#### `tb_charge` e `tb_delivery` — associações de endereço

- `tb_charge`: `charge_id BIGINT AUTO_INCREMENT` PK; `address_id BIGINT NOT NULL` FK para endereço, `ON DELETE CASCADE`.
- `tb_delivery`: `delivery_id BIGINT AUTO_INCREMENT` PK; `address_id BIGINT NOT NULL` FK para endereço, `ON DELETE CASCADE`; `delivery_phrase VARCHAR(255)` opcional.
- As migrations não declaram unicidade em `address_id`; portanto o esquema permite múltiplos registros associados ao mesmo endereço.
- `tb_orders.delivery_id` é apenas índice, sem FK declarada para `tb_delivery`.

#### `tb_card` — cartão de cliente

- `card_id VARCHAR(36)` PK; `customer_id VARCHAR(36) NOT NULL` FK para cliente, `ON DELETE CASCADE`.
- `main BOOLEAN NOT NULL`; `printed_name VARCHAR(255) NOT NULL`; `code CHAR(4) NOT NULL`; `number_card CHAR(16) NOT NULL`; `expiration_date DATE NOT NULL`; `flag VARCHAR(50) NOT NULL`.
- Índices em `customer_id`, `flag`, `main`.
- Dados de cartão e código de segurança são sensíveis. Não exponha valores reais em logs, exemplos, documentação de alterações nem consultas de diagnóstico.

#### `tb_adm` — administrador

- `user_id VARCHAR(36)` PK; `email VARCHAR(100) NOT NULL UNIQUE`; `password VARCHAR(255) NOT NULL` (hash de autenticação).
- Índice adicional em `email` redundante com UNIQUE. Não armazene senha em texto simples.

### Catálogo e estoque

#### `tb_product` — produto base

- `product_id VARCHAR(36)` PK; `product_type VARCHAR(255) NOT NULL`; `active BOOLEAN NOT NULL`.
- `name VARCHAR(255) NOT NULL`; `description TEXT NOT NULL`; `price DECIMAL(19,2) NOT NULL`; `type_coin VARCHAR(50) NOT NULL`.
- `category VARCHAR(50) NOT NULL`; `brand VARCHAR(255) NOT NULL`; `pricing DECIMAL(19,2) NOT NULL` (custo/preço de produção conforme comentário da migration).
- `justification VARCHAR(255)` opcional; `product_status VARCHAR(50)` opcional.
- `created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP`; `updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP`.
- Índices em nome, categoria, marca e preço.
- Relações: detalhes em `tb_book`, `tb_bag` ou `tb_toy` usam o mesmo `product_id` como PK/FK e cascata ao excluir; `tb_midia` pode referenciar produto e usa `ON DELETE SET NULL`; estoque, saída de estoque, item de carrinho e referência do item de pedido também se relacionam ao produto.

#### `tb_midia` — mídia do produto

- `midia_id VARCHAR(36)` PK; `url TEXT NOT NULL`; `description TEXT NOT NULL`; `product_id VARCHAR(36)` opcional.
- FK para produto com `ON DELETE SET NULL`; índice em `product_id`. A mídia permanece quando o produto é excluído, sem associação.

#### `tb_book`, `tb_bag`, `tb_toy` — detalhes por subtipo

- Cada tabela usa `product_id VARCHAR(36)` como PK e FK para `tb_product.product_id`, com `ON DELETE CASCADE`.
- `tb_book`: `synopsis TEXT NOT NULL`; `page INT NOT NULL`; `author VARCHAR(255) NOT NULL`; `edition VARCHAR(255) NOT NULL`; `isbn VARCHAR(25) NOT NULL UNIQUE`; `category_book VARCHAR(255) NOT NULL`; `height`, `width`, `length`, `weight DECIMAL(5,2) NOT NULL`; `publisher_date DATE NOT NULL`. Índices em autor, categoria e data de publicação.
- `tb_bag`: `volume DECIMAL(5,2) NOT NULL`; `color VARCHAR(255) NOT NULL`; índices em volume e cor.
- `tb_toy`: `toy_type VARCHAR(50) NOT NULL`; `age_range VARCHAR(30) NOT NULL`; `material VARCHAR(255) NOT NULL`; `number_of_pieces INT` opcional; `battery_required BOOLEAN NOT NULL DEFAULT FALSE`; `battery_included BOOLEAN NOT NULL DEFAULT FALSE`. CHECK permite `number_of_pieces` nulo ou maior que zero.
- A exclusividade entre subtipos não é imposta por constraint; é responsabilidade do fluxo de aplicação.

#### `tb_stock` — saldo atual por produto

- `stock_id VARCHAR(36)` PK; `product_id VARCHAR(36) NOT NULL` FK para produto; `total_quantity INT`; `quantity_available INT`.
- `product_id` tem UNIQUE: no máximo um saldo por produto. FK não declara ação de exclusão em cascata.
- `total_quantity` inclui reservado segundo o comentário; `quantity_available` representa a quantidade disponível para venda. Não assuma que os campos podem ser alterados independentemente da lógica de reserva.

#### `tb_stock_input` — entrada de estoque

- `stock_input_id VARCHAR(36)` PK; `stock_id VARCHAR(36) NOT NULL` FK para `tb_stock`, `ON DELETE CASCADE`.
- `quantity INT UNSIGNED NOT NULL`; `product_quality VARCHAR(50) NOT NULL`; `entry_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP`.
- `purchase_price DECIMAL(19,2) NOT NULL`; `coin VARCHAR(50) NOT NULL`; `supplier VARCHAR(255) NOT NULL` (texto, sem tabela de fornecedor).

#### `tb_stock_output` — saída de estoque

- `stock_output_id VARCHAR(36)` PK; `order_id VARCHAR(36) NOT NULL` (sem FK declarada); `product_id VARCHAR(36) NOT NULL` FK produto `ON DELETE CASCADE`.
- `quantity INT NOT NULL`; `stock_id VARCHAR(36) NOT NULL` FK estoque `ON DELETE CASCADE`.
- Índices em produto e estoque.

### Carrinho, reserva e pedidos

#### `tb_cart` — carrinho

- `cart_id VARCHAR(36)` PK; `customer_id VARCHAR(36) NOT NULL` FK cliente `ON DELETE CASCADE`.
- `created_at TIMESTAMP NOT NULL`; `updated_at TIMESTAMP`; `total_price DECIMAL(19,2) NOT NULL`; `coin VARCHAR(20) NOT NULL`.
- Índice em `customer_id`.

#### `tb_reservation` — reserva de quantidade

- `reservation_id VARCHAR(36)` PK; `cart_id VARCHAR(36) NOT NULL`; `customer_id VARCHAR(36) NOT NULL`; `stock_id VARCHAR(36) NOT NULL`.
- FKs para carrinho, cliente e estoque, todas `ON DELETE CASCADE`; índices nos três identificadores.
- `quantity INT NOT NULL`; `reservation_time TIMESTAMP NOT NULL`; `reserve_status VARCHAR(50) NOT NULL`.

#### `tb_cart_item` — item do carrinho

- `cart_item_id VARCHAR(36)` PK; `product_id VARCHAR(36) NOT NULL`; `cart_id VARCHAR(36) NOT NULL`; `reservation_id VARCHAR(36) NOT NULL`.
- FKs para produto, carrinho e reserva, todas `ON DELETE CASCADE`; índices em produto e carrinho.
- `product_name VARCHAR(255) NOT NULL` e `url_product VARCHAR(255) NOT NULL` são cópias descritivas; `quantity INT NOT NULL`; `unit_price DECIMAL(19,2) NOT NULL`; `coin VARCHAR(20) NOT NULL`; `subtotal DECIMAL(19,2) NOT NULL`.
- Esquema não garante `subtotal = quantity * unit_price` nem que reserva/carrinho/produto sejam semanticamente correspondentes.

#### `tb_orders` — pedido

- `order_id VARCHAR(36)` PK; `customer_id VARCHAR(36) NOT NULL` (sem FK); `delivery_id VARCHAR(36)` opcional (sem FK; tipo não corresponde ao `BIGINT` de `tb_delivery.delivery_id`).
- `created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP`; `value DECIMAL(19,2) NOT NULL`; `type_coin VARCHAR(10) NOT NULL`.
- `status ENUM('PAID','PENDING') NOT NULL DEFAULT 'PENDING'`; `delivered BOOLEAN NOT NULL`.
- Índices em cliente, entrega e status. Não há FKs declaradas para cliente ou entrega.

#### `tb_order_items` — itens do pedido

- `order_item_id VARCHAR(36)` PK; `order_id VARCHAR(36) NOT NULL` FK para `tb_orders`, `ON DELETE CASCADE`; índice em pedido.
- `product_id VARCHAR(255) NOT NULL` (comentário diz nome histórico, e tipo é textual); `product_ref_id VARCHAR(36)` opcional adicionado em V34, sem FK e com índice; `price DECIMAL(19,2) NOT NULL`; `quantity INT NOT NULL`; `subtotal DECIMAL(19,2) NOT NULL`.
- `item_status ENUM('AWAITING_PAYMENT','CANCELLED','SEPARATING','SHIPPED','DELIVERED','EXCHANGE_REQUEST','EXCHANGE_ACCEPTED','EXCHANGE_REJECTED') NOT NULL DEFAULT 'AWAITING_PAYMENT'`.
- `reservation_id VARCHAR(36) NOT NULL` (comentário chama de referência de saída, mas não existe FK declarada).

#### `tb_replacement` — solicitação de substituição

- `id VARCHAR(36)` PK; `order_id VARCHAR(36) NOT NULL` FK pedido, `ON DELETE CASCADE`; `reservation_id VARCHAR(36) NOT NULL` FK reserva, `ON DELETE CASCADE`; `customer_id VARCHAR(36) NOT NULL` FK cliente, `ON DELETE CASCADE`.
- `reason VARCHAR(50)`, `explain VARCHAR(255)`, `status VARCHAR(50)` opcionais; `quantity INT NOT NULL`.
- Índice em `reservation_id`.

### Pagamentos e vouchers

#### `tb_payment` — pagamento

- `payment_id VARCHAR(255)` PK; `customer_id VARCHAR(255) NOT NULL` (sem FK); `payment_date DATETIME NOT NULL`; `total DECIMAL(19,2) NOT NULL`; `type_coin VARCHAR(20) NOT NULL`; `status_payment VARCHAR(50) NOT NULL`.
- Índices em cliente, status e data.
- `order_id VARCHAR(36)` foi adicionado em V23, FK para `tb_orders.order_id` com `ON DELETE CASCADE`. A coluna não está explicitamente marcada `NOT NULL`.

#### `tb_description_payment` — formas/partes do pagamento

- `id VARCHAR(36)` PK; `type_payment VARCHAR(50) NOT NULL`; `payment_id VARCHAR(255) NOT NULL` FK para pagamento, `ON DELETE CASCADE`; índice em `payment_id`.
- `value DECIMAL(19,2)` existiu inicialmente e foi removido em V22; não faz parte do esquema atual.

#### `tb_voucher` — voucher

- `voucher_id VARCHAR(255)` PK; `customer_id VARCHAR(255) NOT NULL` (sem FK); `message TEXT NOT NULL`; `emission DATE NOT NULL`.
- `type_voucher VARCHAR(50) NOT NULL`; `discount DOUBLE NOT NULL`; `active BOOLEAN NOT NULL DEFAULT TRUE`.
- Índices em cliente, tipo, emissão e ativo.

## Relações principais

```text
tb_customer 1 ── N tb_address, tb_card, tb_cart, tb_reservation
tb_address  1 ── N tb_charge, tb_delivery (sem UNIQUE no lado dependente)
tb_product  1 ── 0/1 tb_book, tb_bag, tb_toy; 1 ── N tb_midia, tb_stock
tb_cart     1 ── N tb_cart_item, tb_reservation
tb_stock    1 ── N tb_stock_input, tb_stock_output, tb_reservation
tb_orders   1 ── N tb_order_items, tb_payment, tb_replacement
tb_payment  1 ── N tb_description_payment
```

O diagrama resume cardinalidades pretendidas/permitidas pelas FKs; relações sem FK documentada são apenas convenções da aplicação. IDs associados a relações sem FK podem ficar órfãos.

## Regras para alterações e UPDATE

1. Para alterações estruturais, inspecione o histórico de migrations e crie uma nova migration `V<n>__descricao.sql`, com versão seguinte e nome descritivo. Nunca reescreva migrations já aplicadas.
2. Antes de adicionar FK, confira tipos e comprimentos nos dois lados. Há divergências existentes (por exemplo `tb_orders.delivery_id` e `tb_delivery.delivery_id`); corrija conscientemente em migration própria, considerando dados existentes e compatibilidade da aplicação.
3. Para atualizar dados existentes, exija uma condição seletiva baseada em PK/índice único e confirme a quantidade/identidade das linhas afetadas antes de executar. Evite UPDATE sem WHERE. Preserve constraints, regras do domínio e estados válidos dos ENUMs.
4. Ajustes de produto, estoque, carrinho, reserva, pedido ou pagamento devem seguir os casos de uso e invariantes do módulo, não apenas alterar uma linha isolada. Confira serviço, domínio, mapper e gateway correspondentes.
5. Não trate totais e subtotais redundantes como automaticamente derivados: confirme a regra no código e atualize dados correlatos na mesma transação quando aplicável.
6. Não registre, mostre ou copie hashes, números de cartão, CVV, tokens ou dados pessoais ao explicar ou depurar o esquema.
7. Ao descrever o estado atual, diferencie claramente uma FK real de uma associação sugerida apenas por nomes ou comentários. Não invente constraints ausentes.

## Fluxo ao usar esta skill

1. Descubra o objetivo e a tabela; leia migrations relevantes em ordem e confirme o esquema final.
2. Rastreie a entidade e o fluxo de persistência antes de propor uma alteração.
3. Explique atributos, nulabilidade, defaults, PK/FK, índices, ações de exclusão e relações relevantes em linguagem direta.
4. Para mudança, proponha migration/update limitado e compatível com o código. Não inclua instruções de INSERT nem conteúdo das migrations de seed.
5. Verifique a migration e o diff; execute validações apenas quando solicitadas ou necessárias para concluir a alteração.
