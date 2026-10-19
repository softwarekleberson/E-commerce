# Módulo `payment`

## Responsabilidade

Coordena o checkout, as estratégias de pagamento e o registro do pagamento. A integração de pagamento configurada atualmente inclui um gateway fake, não um adapter de adquirente externo real.

## Estrutura e arquitetura

- `application/dto/`: detalhes e resultados do pagamento.
- `application/service/payment/`: contrato/implementação de checkout, factory de estratégias e contrato de gateway.
- `application/service/voucher/`: integração de pagamento e pós-uso de voucher.
- `application/usecase/`: estratégias cartão, dois cartões, voucher e combinação cartão/voucher; `contract/PaymentMethod` é o contrato comum.
- `domain/`: pagamento, total, tipo/status, descrição, exceção e `PaymentRepository`.
- `infra/config/`, `controller/`, `fake/`, `gateway/`, `mapper/`, `persistencia/`: composição, HTTP, gateway fake, persistência/adaptação JPA e conversão.

## Casos de uso

- **Checkout**: `PaymentController` recebe `POST /payment/checkout` e delega a `CheckoutImpl`.
- **Escolher método de pagamento**: `PaymentMethodFactoryImpl` seleciona `CardPayment`, `TwoCardsPayment`, `VoucherPayment` ou `CardAndCouponPayment`.
- **Processar voucher**: serviços de `application/service/voucher` tratam as integrações relacionadas ao uso do voucher.

No checkout, a implementação busca cliente/carrinho, endereço principal, cria pedido/itens, calcula e valida total, processa a estratégia, atualiza o pedido/pagamento, confirma reservas, persiste pedido/pagamento, limpa o carrinho e publica evento. Se o pagamento falhar, persiste pedido e pagamento rejeitado e lança exceção. A classe não declara transação explícita; não assumir rollback completo em falha intermediária.

## Referências

- [Controller](infra/controller/PaymentController.java)
- [Checkout](application/service/payment/CheckoutImpl.java)
- [Estratégias](application/usecase/)
- [Gateway fake](infra/fake/payment/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/payment.md)
