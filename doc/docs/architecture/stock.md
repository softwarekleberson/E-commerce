# Módulo `stock`

## Responsabilidade e estrutura

Controla estoque, entradas de produtos, saídas e reservas. `application/dto` modela operações/consultas; `application/usecase` contém criação e entrada de estoque; `domain/productinput`, `productoutput` e `reservation` separam conceitos; `domain` contém `Stock`, valores, exceções e repositório; `infra` contém controller, configuração, gateway, mappers e entidades persistidas.

## Casos de uso identificados

- Criar estoque: `CreateStockImpl`.
- Adicionar entrada de produto ao estoque: `AddProductStockImpl`.
- Consultar e reservar estoque: operações também são chamadas por integrações de `cart`; contratos/endpoints devem ser consultados em `StockController` e `StockRepositoryJpa`, pois não há classe de use case nomeada para cada operação na pasta de aplicação.
- Confirmar reserva no checkout: `CheckoutImpl` localiza o estoque pela reserva e chama `Stock.confirmOrder(productId, reservationId)`.

`Stock` é o agregado de consistência observado para entradas/saídas e reservas. `ReservationMapper`, `StockProductInputMapper` e `StockProductOutputMapper` traduzem os modelos para persistência. A política de expiração/cancelamento de reserva deve ser confirmada nos serviços que a chamam; não assumir prazo implícito.

## Referências

- [Controller](../../../src/main/java/com/cleancode/ecommerce/stock/infra/controller/StockController.java)
- [Use cases](../../../src/main/java/com/cleancode/ecommerce/stock/application/usecase/)
- [Domínio de estoque](../../../src/main/java/com/cleancode/ecommerce/stock/domain/)
- [Gateway e mappers](../../../src/main/java/com/cleancode/ecommerce/stock/infra/)
- [Integrações do carrinho](../../../src/main/java/com/cleancode/ecommerce/cart/application/service/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/stock/)

