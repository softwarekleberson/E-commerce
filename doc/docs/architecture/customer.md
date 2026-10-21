# Módulo `customer`

## Responsabilidade e estrutura

Modela cliente, contatos, endereços e cartões. `application/dtos` organiza payloads por assunto; `application/usecase` contém operações e contratos; `domain/customer`, `domain/address`, `domain/card` e `domain/contact` organizam entidades e tipos; `infra/controller`, `gateway`, `mapper` e `persistence` fazem a borda HTTP e JPA.

## Casos de uso identificados

- **Cadastro e perfil:** `CreateCustomerImpl`, `ListCustomerImpl`, `ListAllCustomersImpl`, `UpdateCustomerImpl`.
- **Senha e elegibilidade:** `UpdatePasswordImpl`, `PasswordValidationCheckImpl`, `CustomerCanBuy` e contrato `CustomerIsActive`.
- **Endereço de cobrança:** `CreateCustomerChargeImpl`, `UpdateChargeImpl`, `DeleteChargeImpl`.
- **Endereço de entrega:** `CreateCustomerDeliveryImpl`, `UpdateDeliveryImpl`, `DeleteDeliveryImpl`.
- **Cartão:** `CreateCardImpl`.
- **Ativação administrativa:** `ChangeActivationStatusAdmImpl`.

`CustomerController` expõe perfil, senha, endereços, cartão e consulta de vouchers do cliente. `AdmCustomerController` atende operações administrativas e paginação geral. Os endpoints associados estão nos controllers; autenticação é validada pelo controller em várias operações e o email autenticado é encaminhado à aplicação. Não foi inferida uma rota para cada contrato sem conferir os mapeamentos.

## Pastas

O domínio divide os conceitos para manter regras e validações próximas a cada entidade. `domain/card/contract/PaymentGateway` é uma porta do domínio; adapters JPA ficam agrupados por endereço, cartão e cliente. Mappers independentes traduzem cada agregado/tipo persistido. DTOs de entrada e saída ficam por assunto em `application/dtos`.

## Referências

- [Controller do cliente](../../../src/main/java/com/cleancode/ecommerce/customer/infra/controller/CustomerController.java)
- [Casos de uso](../../../src/main/java/com/cleancode/ecommerce/customer/application/usecase/)
- [Modelo de domínio](../../../src/main/java/com/cleancode/ecommerce/customer/domain/)
- [Persistência e mappers](../../../src/main/java/com/cleancode/ecommerce/customer/infra/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/customer/)

