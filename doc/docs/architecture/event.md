# Módulo `event`

## Responsabilidade e arquitetura

Agrupa contratos, payloads, publishers e listeners de eventos usados para reduzir chamadas diretas entre reações de módulos. A implementação observada usa eventos Spring no mesmo processo; não há evidência nesta pasta de broker distribuído.

## Pastas por fluxo

- `product`: `EventPublisher`, `SpringEventPublisher`, `ProductEvent` e listeners `ActiveProductEventListener`, `CreatedStockEventListener`, `SellingPriceEventListener`.
- `order`: `OrderEvent`, `EventTransportPublisher`, `SpringProductEventPublisher` e `OrderItemSeparationEventListener`.
- `replacement`: publicação de `ReplacementEvent` e listener `CreateVoucherEventListener`.
- `ExchangeRequestAfterReview`: publicação e reação ao resultado da revisão de pedido de troca, incluindo `ExchangeEventAfterReviewAdm` e `ReceiptOfExchangeRequest`.
- `stock`: `StockUpdatedEvent`, payload de atualização de estoque.

## Casos de uso / reações identificadas

1. A ativação/criação/alteração de preço do produto dispara listeners de produto/estoque (confirmar o gatilho e as condições lendo publicadores/listeners relacionados).
2. O checkout publica `OrderEvent` após persistir pedido/pagamento e limpar o carrinho; listener de separação reage ao evento de pedido.
3. Eventos de substituição e de revisão de solicitação conectam a decisão administrativa a outras reações, inclusive geração de voucher conforme `CreateVoucherEventListener`.

Os nomes apontam as responsabilidades, mas a ordem, tratamento de falha e garantias de entrega são locais ao código Spring e devem ser tratados como síncronos/locais somente após verificar configuração e anotações dos listeners.

## Referências

- [Código de eventos](../../../src/main/java/com/cleancode/ecommerce/event/)
- [Checkout que publica o evento do pedido](../../../src/main/java/com/cleancode/ecommerce/payment/application/service/payment/CheckoutImpl.java)
- [ADR de eventos locais](../adr/0007-in-process-domain-events.md)

