# Módulo `customer`

## Responsabilidade

Gerencia cadastro, perfil, contatos, endereços de cobrança/entrega e cartões de clientes. Os vouchers são consultados por integração com o módulo `promotional`.

## Estrutura e arquitetura

- `application/dtos/address`, `card`, `customer`: dados de entrada/saída agrupados por assunto.
- `application/usecase/`: implementação dos casos de uso; `contract/` contém contratos das operações.
- `domain/address`, `card`, `contact`, `customer`: entidades e tipos específicos de endereço, cartão, contato e cliente. `domain/customer/repository/` define `CustomerRepository`; cartão também possui um contrato de gateway.
- `infra/config/`: configuração; `controller/`: API; `gateway/`: persistência/adaptação; `mapper/`: conversão; `persistence/` separa entidades de endereço, cartão e cliente.

## Casos de uso encontrados

- Cliente: criar (`CreateCustomerImpl`), consultar um/todos (`ListCustomerImpl`, `ListAllCustomersImpl`), atualizar perfil (`UpdateCustomerImpl`) e alterar ativação administrativa (`ChangeActivationStatusAdmImpl`).
- Acesso e elegibilidade: atualizar senha (`UpdatePasswordImpl`), verificar senha (`PasswordValidationCheckImpl`) e avaliar compra (`CustomerCanBuy`; contrato de consulta de atividade em `CustomerIsActive`).
- Endereços de cobrança: criar, atualizar e excluir (`CreateCustomerChargeImpl`, `UpdateChargeImpl`, `DeleteChargeImpl`).
- Endereços de entrega: criar, atualizar e excluir (`CreateCustomerDeliveryImpl`, `UpdateDeliveryImpl`, `DeleteDeliveryImpl`).
- Cartão: criar (`CreateCardImpl`).

`CustomerController` fornece perfil, senha, endereços, cartão e consulta paginada de vouchers. Autorização administrativa e listagens gerais também passam por `AdmCustomerController`.

## Referências

- [Controller do cliente](infra/controller/CustomerController.java)
- [Use cases](application/usecase/)
- [Domínio](domain/)
- [Persistência e mappers](infra/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/customer.md)
