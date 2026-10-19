# Módulo `adm`

## Responsabilidade e estrutura

Reúne operações administrativas expostas por controllers próprios. O pacote `application` contém criação de administrador e consultas/relatórios; `domain` contém `Adm`, repositórios, exceções e tipos de relatório; `infra` contém controllers, configuração, adapters JPA, mapper, entidades e cache de pedidos.

## Casos de uso identificados

- **Criar administrador** — `CreateNewAdm` / `CreateNewAdmImpl`, exposto por `AdmCreateAdm`.
- **Operar clientes, pedidos, vouchers e substituições como administrador** — endpoints em `AdmCustomerController`, `AdmOrdersController`, `AdmVoucherController` e `AdmReplacement`. Os controllers delegam para contratos de outros módulos; a regra detalhada pertence aos casos de uso chamados.
- **Consultar dashboard e vendas por categoria** — `SalesDashboard` e `SalesByCategory` usam `SalesReportRepository`; `SalesReportRepositoryJpa` é o adapter de consulta.
- **Remover produto de carrinho** — `RemoveProductCart` está no domínio, sem implementação de aplicação correspondente localizada na árvore. Não foi possível confirmar um fluxo ativo deste caso de uso.

## Pastas e dependências

`application/dto` transporta dados de criação; `application/report` compõe relatórios. `domain/report` define pontos de relatório e contrato de consulta; `domain/repository` define persistência administrativa. `infra/controller` é a entrada HTTP, `infra/gateway` implementa contratos, `infra/persistence` contém `AdmEntity`, `infra/mapper` converte domínio/entidade e `infra/service` mantém cache de pedidos.

## Referências

- [Controllers](../../../src/main/java/com/cleancode/ecommerce/adm/infra/controller/)
- [Casos de uso e relatórios](../../../src/main/java/com/cleancode/ecommerce/adm/application/)
- [Modelo e contratos do domínio](../../../src/main/java/com/cleancode/ecommerce/adm/domain/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/adm/)

