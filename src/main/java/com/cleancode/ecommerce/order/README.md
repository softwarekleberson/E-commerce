# Módulo `order`

## Responsabilidade

Representa pedidos e seus itens, incluindo ciclo de vida do pedido e estados individuais dos itens. O carrinho pertence ao módulo `cart`.

## Estrutura e arquitetura

- `application/service/`: serviço de identidade usado nos fluxos do pedido.
- `application/usecase/order/`: consultas e confirmação de entrega; `contract/` define suas portas e `dto/` os modelos de listagem.
- `application/usecase/item/`: operações/transições de estado de item; `contract/` contém as interfaces correspondentes.
- `domain/`: `Order`, `OrderItem`, IDs, exceções e `OrderRepository`.
- `domain/state/order/`: estados e status do pedido; `domain/state/itens/`: estados e status do item.
- `infra/config/`, `controller/`, `gateway/`, `mapper/`, `persistencia/`: composição, HTTP, adaptação JPA, conversão e entidades persistidas. A grafia `persistencia` é a do pacote existente.

## Casos de uso encontrados

- Listar pedidos do cliente: `ListOrdersImpl`, exposto por `GET /customer/orders` com paginação.
- Cancelar item do pedido: `CancelledOrderImpl`, exposto por `PUT /customer/orders/{orderId}/cancelled/{reservationId}`.
- Listar todos os pedidos e pedidos por transporte: `ListAllOrdersImpl` e `ListOrderByTransportImpl`, usados por fluxos administrativos.
- Confirmar entrega pelo cliente: `CustomerConfirmsDeliveryImpl`.
- Transições de item: `AwaitingPaymentImpl`, `SeparationOrderImpl`, `TransportOrderImpl`, `DeliveredOrderImpl`, `CancelledOrderImpl`, `OrderStatusAfterInitiatingAnExchangeRequestImpl` e `OrderStatusAfterReviewImpl`.

O estado do agregado `Order` é distinto do estado de cada `OrderItem`. `Order.pay()` valida total positivo e delega a transição para `OrderState`; as transições do item também são mediadas por `ItemState`.

## Referências

- [Controller do cliente](infra/controller/OrderController.java)
- [Agregado Order](domain/Order.java)
- [Estados](domain/state/)
- [Use cases](application/usecase/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/order.md)
