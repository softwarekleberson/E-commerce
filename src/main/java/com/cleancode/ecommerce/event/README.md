# Módulo `event`

## Responsabilidade

Define eventos, publicadores e listeners que conectam reações entre módulos. A implementação encontrada é baseada em eventos Spring locais ao processo; este pacote não implementa broker distribuído.

## Organização das pastas

- `product/`: `ProductEvent`, `StockUpdatedEvent`, contrato e adapter de publicação, além de listeners de ativação, criação de estoque e preço.
- `stock/`: payload de evento de atualização de estoque.
- `order/`: `OrderEvent`, publisher de transporte e listener de separação de itens.
- `replacement/`: evento/publicador de substituição e listener de criação de voucher.
- `ExchangeRequestAfterReview/`: evento e componentes de publicação/reação a revisão administrativa de solicitação de troca.

## Fluxos/casos de uso relacionados

- O checkout publica `OrderEvent` após gravar pedido/pagamento e limpar o carrinho; `OrderItemSeparationEventListener` reage ao evento.
- Eventos de produto e estoque permitem que listeners reajam a mudanças; o efeito específico depende do payload e do listener correspondente.
- Eventos de substituição/revisão conectam a solicitação aos efeitos posteriores, incluindo o listener que solicita criação de voucher.

Não assumir entrega assíncrona, persistência, retry ou garantia de atomicidade sem configuração específica: os tipos e adapters deste pacote usam infraestrutura Spring local.

## Referências

- [Eventos do módulo](./)
- [Checkout publicador do evento de pedido](../payment/application/service/payment/CheckoutImpl.java)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/event.md)
