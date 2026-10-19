# Módulo `promotional`

## Responsabilidade

Modela vouchers e descontos e fornece operações de criação, consulta e integração com substituições e pagamentos.

## Estrutura e arquitetura

- `domain/`: `Voucher`, `VoucherId`, `CodeVoucher`, `Discount`, `Message`, `TypeVoucher`, exceção e `VoucherRepository`.
- `usecase/`: implementações `CreateVoucherImpl`, `ListVoucherCustomerImpl`, `ReplacementVoucherImpl` e `VoucherAfterUseImpl`.
- `usecase/contract/`: interfaces de casos de uso.
- `usecase/dto/`: dados de criação/listagem e par valor/identificador do cliente.
- `usecase/service/contract/`: dependências de identidade, reserva, substituição e pós-uso.
- `infra/config/`, `gateway/`, `mapper/`, `persistence/`: configuração, adapter JPA, conversão e entidades persistidas.

Este módulo é uma variação da estrutura recorrente: os casos de uso estão em `usecase/`, não em `application/usecase/`.

## Casos de uso encontrados

- Criar voucher: `CreateVoucherImpl`.
- Listar vouchers do cliente: `ListVoucherCustomerImpl`, também chamado pelo `CustomerController`.
- Gerar voucher para substituição: `ReplacementVoucherImpl`.
- Executar ações posteriores ao uso: `VoucherAfterUseImpl`.

Validade, aplicação do desconto e efeitos de uso devem ser explicados a partir das implementações e do domínio; os nomes dos campos, isoladamente, não definem essas regras.

## Referências

- [Casos de uso](usecase/)
- [Domínio](domain/)
- [Infraestrutura](infra/)
- [Listener relacionado à substituição](../event/replacement/CreateVoucherEventListener.java)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/promotional.md)
