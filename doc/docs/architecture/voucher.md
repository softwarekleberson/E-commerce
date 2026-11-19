# Módulo `voucher`

## Responsabilidade e estrutura

Modela vouchers e descontos. O módulo tem uma estrutura própria: os casos de uso ficam em `usecase/`, não em `application/usecase`. `domain` contém voucher, código, desconto, tipo, identificador, exceção e `VoucherRepository`; `infra` contém configuração, gateway, mapper e entidades JPA.

## Casos de uso identificados

- Criar voucher: `CreateVoucherImpl`.
- Listar vouchers do cliente: `ListVoucherCustomerImpl`, também chamado pelo `CustomerController`.
- Criar voucher de substituição: `ReplacementVoucherImpl`.
- Reagir ao uso de voucher: `VoucherAfterUseImpl`.

`usecase/dto` contém dados de criação/listagem e identificação de cliente/valor; `usecase/service/contract` define colaborações com identidade, reserva, substituição e pós-uso. A semântica de validade, saldo e aplicação do desconto reside no domínio e nas implementações; não inferir regras apenas pelo nome das propriedades.

## Referências

- [Casos de uso](../../../src/main/java/com/cleancode/ecommerce/voucher/usecase/)
- [Domínio](../../../src/main/java/com/cleancode/ecommerce/voucher/domain/)
- [Infraestrutura](../../../src/main/java/com/cleancode/ecommerce/voucher/infra/)
- [Listener de criação de voucher](../../../src/main/java/com/cleancode/ecommerce/event/replacement/CreateVoucherEventListener.java)
- [Guia local do módulo](../../../src/main/java/com/cleancode/ecommerce/voucher/README.md)
