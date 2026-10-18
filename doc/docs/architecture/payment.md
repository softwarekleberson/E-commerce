# Módulo `payment`

## Responsabilidade e estrutura

Coordena checkout, representação de pagamento e estratégias. `application/dto` contém dados e resultados; `application/service/payment` contém `Checkout`, factory e cliente de gateway; `application/service/voucher` concentra integração com voucher; `application/usecase` contém estratégias; `domain` contém `Payment`, total, tipo/status, descrição, exceção e repositório; `infra` contém controller, configuração, fake, gateway JPA, mapper e entidades persistidas.

## Casos de uso

- **Checkout** — `CheckoutImpl.execute(email, PaymentDetails)`, endpoint `POST /payment/checkout`.
- **Pagamento com cartão, dois cartões, voucher ou cartão + voucher** — `CardPayment`, `TwoCardsPayment`, `VoucherPayment`, `CardAndCouponPayment` implementam `PaymentMethod`; `PaymentMethodFactoryImpl` escolhe a estratégia.
- **Aplicar voucher após uso** — serviços em `application/service/voucher` encapsulam as integrações relacionadas.

### Fluxo de checkout confirmado

1. Busca cliente pelo email e carrinho do cliente; obtém endereço de entrega principal.
2. Monta `Order` com itens do carrinho e calcula total; rejeita total menor ou igual a zero.
3. Cria `Payment`, adiciona descrições e escolhe estratégia por factory.
4. Se o resultado falha, salva pedido, marca pagamento como rejeitado, persiste pagamento e lança `IllegalDomainPayment`.
5. Se aprovado, marca pedido pago e pagamento aprovado; confirma cada reserva no estoque; salva pedido e pagamento; remove itens do carrinho e salva; publica evento de pedido.

As operações são sequenciais na implementação. `CheckoutImpl` não declara transação, portanto não assumir rollback integral em falha intermediária. `PaymentController` obtém o email da autenticação e valida o corpo.

## Referências

- [Checkout](../../../src/main/java/com/cleancode/ecommerce/payment/application/service/payment/CheckoutImpl.java)
- [Controller](../../../src/main/java/com/cleancode/ecommerce/payment/infra/controller/PaymentController.java)
- [Estratégias](../../../src/main/java/com/cleancode/ecommerce/payment/application/usecase/)
- [Gateway fake](../../../src/main/java/com/cleancode/ecommerce/payment/infra/fake/payment/)
- [ADR Strategy e Factory](../adr/0005-payment-strategy-and-factory.md)

Não foi encontrada uma pasta de testes específica para `payment` em `src/test/java`.
