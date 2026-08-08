# Módulo Order (Pedido / Carrinho)

Este diretório contém o módulo de **Order** (Pedido) do sistema Clean Ecommerce. Atualmente, a principal responsabilidade deste módulo está focada na gestão do **Carrinho de Compras (Cart)**, permitindo aos clientes adicionar produtos, alterar quantidades, limpar o carrinho e iniciar o processo de reserva de estoque para futura finalização do pedido.

## 🏗️ Arquitetura do Software

Assim como o restante do sistema, o módulo segue a **Clean Architecture** e o **Domain-Driven Design (DDD)**, estruturado nas seguintes camadas:

1. **Domain (`domain`)**:
   - Focado no subdomínio de **Cart** (Carrinho).
   - Contém a entidade principal `Cart` que atua como **Aggregate Root** (Raiz de Agregação) para seus itens (`CartItens`).
   - Define os identificadores (`CartId`, `CartItemId`) e Value Objects específicos daquele contexto, e reaproveita conceitos compartilhados como `Quantity` e `Price`.
   - Contém a interface do repositório (`CartRepository`), ditando como os dados do domínio devem ser persistidos, mas sem implementar a lógica de banco de dados.

2. **Application (`application`)**:
   - Abriga os **Casos de Uso** referentes ao carrinho (ex: `AddProductToCartImpl`, `UpdateCartImpl`, `DeleteAllCartImpl`).
   - Gerencia a orquestração entre múltiplos domínios. Por exemplo, ao adicionar um item no carrinho (`AddProductToCartImpl`), a aplicação consulta o cliente, o produto e realiza a **reserva de estoque**, comunicando-se com os repositórios dos módulos `customer`, `product` e `stock`.
   - Inclui serviços de aplicação e DTOs para comunicação com a camada externa.

3. **Infrastructure (`infra`)**:
   - Camada externa contendo o `CartController`, que provê os endpoints REST da API de carrinho.
   - Implementa a persistência concreta (JPA) e a integração entre as classes de domínio e o banco de dados.

## 🧩 Padrões de Projeto (Design Patterns)

- **Aggregate Root**: `Cart` encapsula a lista de `CartItens`. Nenhuma operação externa altera um item do carrinho diretamente; toda alteração (como adicionar um produto ou mudar a quantidade) passa pela entidade `Cart`, que garante a consistência e recalcula os totais.
- **Dependency Injection (Injeção de Dependência)**: Casos de uso recebem diversas dependências pelo construtor (ex: `CustomerRepository`, `ProductRepository`, `StockRepository`, `CartRepository`), mantendo-os desacoplados e altamente testáveis.
- **Repository Pattern**: Centraliza as operações de leitura e gravação no banco de dados usando interfaces.
- **Anti-Corruption Layer (Camada de Anticorrupção)**: Embora de forma leve, o módulo converte as saídas dos repositórios de outros módulos (Stock, Customer) em entidades que ele pode trabalhar antes de processar a lógica do carrinho.

## 🛡️ Princípios SOLID

- **S - Single Responsibility Principle**: O objeto `Cart` tem a única responsabilidade de gerenciar as regras de negócio de um carrinho (somar o total, verificar se o produto já existe). Os Casos de Uso executam fluxos operacionais bem específicos.
- **O - Open/Closed Principle**: Novos casos de uso do carrinho podem ser incluídos implementando novas interfaces no pacote `contract` sem alterar os métodos atuais.
- **L - Liskov Substitution Principle**: O design garante que instâncias do domínio usem as implementações de infraestrutura polimorficamente via `CartRepository`.
- **I - Interface Segregation Principle**: Múltiplos contratos pequenos, ao invés de um grande "CartService".
- **D - Dependency Inversion Principle**: A dependência é invertida nas fronteiras arquiteturais. O `AddProductToCartImpl` depende da abstração de `StockRepository` e não da sua implementação concreta (JPA), o que permite mudanças fáceis na tecnologia de persistência do estoque.

## 🏋️‍♂️ Object Calisthenics

1. **Apenas um nível de indentação por método**: Código estruturado de forma plana com *early returns* e tratamento de exceções lançados rapidamente.
2. **Não use `else`**: As verificações de integridade (`if (!removed) throw...`) evitam fluxos de código longos e aninhados.
3. **Envolva primitivos em objetos**: Preços (`Price`), Identificadores (`CartId`, `ProductId`), Quantidades (`Quantity`) são objetos com suas próprias validações semânticas.
4. **Coleções de Primeira Classe**: A lista de `CartItens` é gerenciada estritamente pela classe `Cart`. Para obter a lista, o domínio devolve uma cópia não modificável (`Collections.unmodifiableList(this.cartItens)`), protegendo o estado interno.
5. **Não abrevie**: Casos de uso e variáveis têm nomes verbosos, porém muito descritivos (ex: `ValidateProductHasStock`, `recalculateTotalPrice`).
6. **Nenhum getter/setter cego**: O preço total (`totalPrice`) e a data de modificação (`updatedAt`) são atualizados internamente através do método privado `recalculateTotalPrice()` sempre que uma ação de negócio ocorre (adição/remoção de itens), e não acessados via `setTotalPrice`.