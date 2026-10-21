# Módulo `promotional`

## Responsabilidade e estrutura

Modela vouchers e descontos. O módulo tem uma variação em relação ao padrão dos demais: os casos de uso estão na raiz `usecase/`, não em `application/usecase`. `domain` contém voucher, código, desconto, tipo, identificador, exceção e `VoucherRepository`; `infra` contém configuração, gateway, mapper e entidades JPA.

## Casos de uso identificados

- Criar voucher: `CreateVoucherImpl`.
- Listar vouchers do cliente: `ListVoucherCustomerImpl` (consumido também pelo `CustomerController`).
- Criar voucher de substituição: `ReplacementVoucherImpl`.
- Reagir ao uso de voucher: `VoucherAfterUseImpl`.

`usecase/dto` contém dados de criação/listagem e identificação de cliente/valor; `usecase/service/contract` define colaborações com identidade, reserva, substituição e pós-uso. A semântica de validade, saldo e aplicação do desconto reside no domínio e nas implementações; não inferir regras apenas pelo nome das propriedades.

## Referências

- [Casos de uso](../../../src/main/java/com/cleancode/ecommerce/promotional/usecase/)
- [Domínio](../../../src/main/java/com/cleancode/ecommerce/promotional/domain/)
- [Infraestrutura](../../../src/main/java/com/cleancode/ecommerce/promotional/infra/)
- [Listener de criação de voucher](../../../src/main/java/com/cleancode/ecommerce/event/replacement/CreateVoucherEventListener.java)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/promotional/)

