# Módulo `adm`

## Responsabilidade

Agrupa operações administrativas, criação de administrador e consultas de vendas. Também fornece controllers administrativos que encaminham operações de clientes, pedidos, vouchers e substituições para os módulos correspondentes.

## Estrutura e arquitetura

- `application/`: `CreateNewAdm` e sua implementação; `dto/` contém dados para criação; `report/` monta dashboard e dados de vendas por categoria.
- `domain/`: entidade `Adm`, exceção, contrato `AdmRepository`, operação de gerenciamento `RemoveProductCart` e tipos/contratos de relatório.
- `infra/config/`: composição de dependências do módulo.
- `infra/controller/`: endpoints administrativos (`AdmCreateAdm`, `AdmCustomerController`, `AdmOrdersController`, `AdmReplacement`, `AdmSalesDashboardController`, `AdmVoucherController`).
- `infra/gateway/`: adapters JPA para administrador e relatórios.
- `infra/mapper/` e `infra/persistence/`: conversão e representação persistida de `Adm`.
- `infra/service/`: `AdmOrdersCacheService`, serviço de cache de pedidos administrativos.

## Casos de uso encontrados

- Criar administrador: `CreateNewAdmImpl`, exposto por `AdmCreateAdm`.
- Consultar dashboard e vendas por categoria: `SalesDashboard` e `SalesByCategoryImpl`, apoiados por `SalesReportRepository`/`SalesReportRepositoryJpa`.
- Operações administrativas de clientes, pedidos, vouchers e substituições: pontos de entrada nos controllers indicados. A execução de negócio é delegada a contratos de outros módulos.
- `RemoveProductCart` está declarado no domínio, mas não foi localizada implementação correspondente em `application`; não há evidência suficiente para descrever um fluxo executável.

## Referências

- [Controllers](infra/controller/)
- [Aplicação e relatórios](application/)
- [Domínio](domain/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/adm.md)
