# Módulo `cart`

## Responsabilidade e estrutura

Mantém carrinho e itens, integra catálogo e estoque e coordena reservas. `application/dtos` separa entradas/saídas; `application/service` contém integrações e seus contratos/DTOs; `application/usecase` expõe operações; `domain` contém `Cart`, `CartItem`, identificadores, exceção e contrato de repositório; `infra` contém controller, configuração, gateway, mapper e entidades JPA.

## Casos de uso identificados

| Caso | Implementação | Entrada HTTP |
|---|---|---|
| Adicionar produto | `AddProductToCartImpl` | `POST /customer/cart` |
| Consultar carrinho | `ListCartImpl` | `GET /customer/cart` |
| Atualizar item/quantidade | `UpdateCartImpl` | `PUT /customer/cart/update/{cartItemId}` |
| Remover item | `DeleteUniqueProductCartImpl` | `DELETE /customer/cart/item/{cartItemId}` |
| Limpar carrinho | `DeleteAllCartImpl` | Caso de uso presente; rota não identificada no `CartController` |
| Expirar reservas | `ExpireCartReservationsImpl` | Rotina automática a cada 30 segundos |

O controller usa o email da autenticação como identidade e retorna `401` quando ela é ausente/anônima. Os serviços de aplicação separam consultas ao catálogo, validação de estoque, atualização de reserva e cancelamento de reserva. A política exata de compensação deve ser lida em cada implementação antes de assumir atomicidade entre carrinho e estoque.

## Prazo e expiração da reserva

O administrador consulta e altera o prazo máximo em minutos em `GET` e `PUT /adm/cart-reservation-timeout`. O padrão é 30 minutos; somente valores positivos são aceitos. A configuração fica em `tb_cart_reservation_settings`.

O carrinho é considerado expirado quando `tb_cart.updated_at` ultrapassa o prazo configurado (carrinhos legados sem `updated_at` usam `created_at`). A rotina automática cancela as reservas ativas, move os produtos para a lista de desejos e remove os itens do carrinho. A remoção manual segue o mesmo fluxo e registra a data em `updated_at`. Ao adicionar um produto novamente pelo endpoint existente do carrinho, uma nova reserva é feita e a entrada correspondente sai da lista de desejos.

Clientes consultam a lista em `GET /customer/wishlist` e podem remover um produto com `DELETE /customer/wishlist/{productId}`. As entradas ficam em `tb_wishlist_item`; não mantêm reservas de estoque.

## Pastas

`application/service/contract` define portas para integração; `application/service/dto` registra resultados dessas chamadas; `domain/repository/CartRepository` abstrai persistência; `infra/gateway/CartRepositoryJpa` adapta JPA; `infra/mapper` converte carrinho/itens; `infra/persistence` contém `CartEntity`, `CartItemEntity` e tipo de moeda.

## Referências

- [Controller](../../../src/main/java/com/cleancode/ecommerce/cart/infra/controller/CartController.java)
- [Use cases](../../../src/main/java/com/cleancode/ecommerce/cart/application/usecase/)
- [Serviços de integração](../../../src/main/java/com/cleancode/ecommerce/cart/application/service/)
- [Domínio](../../../src/main/java/com/cleancode/ecommerce/cart/domain/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/cart/)
