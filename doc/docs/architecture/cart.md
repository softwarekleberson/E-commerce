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

O controller usa o email da autenticação como identidade e retorna `401` quando ela é ausente/anônima. Os serviços de aplicação separam consultas ao catálogo, validação de estoque, atualização de reserva e cancelamento de reserva. A política exata de compensação deve ser lida em cada implementação antes de assumir atomicidade entre carrinho e estoque.

## Pastas

`application/service/contract` define portas para integração; `application/service/dto` registra resultados dessas chamadas; `domain/repository/CartRepository` abstrai persistência; `infra/gateway/CartRepositoryJpa` adapta JPA; `infra/mapper` converte carrinho/itens; `infra/persistence` contém `CartEntity`, `CartItemEntity` e tipo de moeda.

## Referências

- [Controller](../../../src/main/java/com/cleancode/ecommerce/cart/infra/controller/CartController.java)
- [Use cases](../../../src/main/java/com/cleancode/ecommerce/cart/application/usecase/)
- [Serviços de integração](../../../src/main/java/com/cleancode/ecommerce/cart/application/service/)
- [Domínio](../../../src/main/java/com/cleancode/ecommerce/cart/domain/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/cart/)

