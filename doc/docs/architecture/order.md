# Módulo `order`

## Responsabilidade e estrutura

Representa pedidos, itens e seus ciclos de vida. `application/usecase/order` reúne consultas e confirmação de entrega; `application/usecase/item` contém ações/contratos de estado de item; `domain/state/order` modela estados do pedido e `domain/state/itens` estados de item; `domain` contém `Order`, `OrderItem`, identificadores, exceções e repositório; `infra` contém controller, configuração, gateway, mapper e persistência (pasta escrita `persistencia`).

## Casos de uso identificados

- **Listar pedidos do cliente:** `ListOrdersImpl`; endpoint `GET /customer/orders`, paginado e associado ao email autenticado.
- **Cancelar item de pedido:** `CancelledOrderImpl`; chamado por `PUT /customer/orders/{orderId}/cancelled/{reservationId}`.
- **Listagem administrativa/por transporte:** `ListAllOrdersImpl` e `ListOrderByTransportImpl`; verificar controladores administrativos para os filtros e autorização concretos.
- **Confirmar entrega pelo cliente:** `CustomerConfirmsDeliveryImpl`; contrato presente em `application/usecase/order`.
- **Transições de item:** `AwaitingPaymentImpl`, `SeparationOrderImpl`, `TransportOrderImpl`, `DeliveredOrderImpl`, `CancelledOrderImpl`, `OrderStatusAfterInitiatingAnExchangeRequestImpl`, `OrderStatusAfterReviewImpl` implementam contratos de transição/reação.

`Order` delega pagamento a `OrderState` e alterações de itens a `ItemState`. O domínio calcula total a partir dos subtotais e localiza item pela reserva. Não confundir o estado do pedido com os estados individuais dos itens: são dois modelos distintos.

## Pastas

`application/service` contém serviço de identidade; `application/usecase/order/dto` representa listagens; `domain/repository` é a porta de persistência; `infra/gateway` e `infra/mapper` adaptam JPA; entidades de pedido e item ficam em `infra/persistencia`.

## Referências

- [Controller de pedidos do cliente](../../../src/main/java/com/cleancode/ecommerce/order/infra/controller/OrderController.java)
- [Agregado Order](../../../src/main/java/com/cleancode/ecommerce/order/domain/Order.java)
- [Estados](../../../src/main/java/com/cleancode/ecommerce/order/domain/state/)
- [Casos de uso](../../../src/main/java/com/cleancode/ecommerce/order/application/usecase/)

Não foi encontrada uma pasta de testes específica para `order` em `src/test/java`.
