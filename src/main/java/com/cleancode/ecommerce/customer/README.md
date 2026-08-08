# Módulo Customer (Cliente)

Este diretório contém o módulo de **Customer** (Cliente) do sistema Clean Ecommerce. O módulo é responsável por gerenciar toda a lógica de negócios e as operações relacionadas aos clientes, como cadastro, atualização de dados, gerenciamento de endereços de entrega e cobrança, bem como cartões de crédito.

## 🏗️ Arquitetura do Software

O módulo segue os princípios da **Clean Architecture** e do **Domain-Driven Design (DDD)**. A estrutura é dividida em três camadas principais para garantir um baixo acoplamento e alta coesão:

1. **Domain (`domain`)**:
   - É o coração da aplicação. Não possui dependências de frameworks externos ou bibliotecas de infraestrutura.
   - Contém **Entidades** ricas (como `Customer`, `Card`, `Delivery`, `Charge`) e **Value Objects** (como `Name`, `Email`, `Cpf`, `Phone`, `Password`, `Birth`).
   - Define os contratos dos repositórios (`CustomerRepository`) que serão implementados na camada de infraestrutura, garantindo a Inversão de Dependência.
   - Regras de negócio e validações intrínsecas ao domínio residem aqui.

2. **Application (`application`)**:
   - Responsável pelos **Casos de Uso** (Use Cases) da aplicação.
   - Orquestra o fluxo de dados entre os objetos de domínio e os serviços externos.
   - Contém **DTOs** (Data Transfer Objects) para entrada e saída de dados, garantindo que as entidades de domínio não vazem para a camada de infraestrutura/apresentação.
   - Implementa os contratos (interfaces) definidos no pacote `contract` (ex: `CreateCustomerImpl`, `UpdateCustomerImpl`).

3. **Infrastructure (`infra`)**:
   - A camada mais externa, que lida com detalhes técnicos e frameworks (Spring Boot, Spring Data JPA, Spring Security).
   - Contém **Controllers** REST (`CustomerController`) que expõem os endpoints da API.
   - Implementa os repositórios (`CustomerRepositoryJpa`) utilizando JPA/Hibernate para persistência no banco de dados.
   - Define a configuração de beans e mapeamentos necessários.

## 🧩 Padrões de Projeto (Design Patterns)

O módulo utiliza diversos padrões de projeto para manter o código limpo e extensível:

- **Dependency Injection (Injeção de Dependência)**: Amplamente utilizado nas classes de Use Case e Controllers (via construtor) para gerenciar dependências de forma flexível e testável.
- **Repository Pattern**: O domínio define a interface `CustomerRepository`, abstraindo como e onde os dados são salvos. A infraestrutura provê a implementação concreta.
- **Factory / Builder (Implícito)**: A criação de entidades complexas como `Customer` é feita com o auxílio de DTOs que atuam como fábricas (`dto.createCustomer()`).
- **Publisher-Subscriber / Observer**: Utilizado na emissão de eventos de domínio (ex: `EventPublisher` disparando o `NewCustomerEvent` na criação de um cliente).
- **Strategy / State**: Lógica de transição de status em métodos como `changeActivationStatusByAdmin()` e `checkActivationRequirements()`.

## 🛡️ Princípios SOLID

O módulo é estritamente desenhado com base no SOLID:

- **S - Single Responsibility Principle**: Classes têm uma única razão para mudar. Cada caso de uso (ex: `CreateCustomerImpl`, `UpdatePasswordImpl`) tem um único propósito claro. O controlador REST apenas delega chamadas, não contendo lógica de negócio.
- **O - Open/Closed Principle**: O sistema é aberto para extensão, mas fechado para modificação. Ao adicionar uma nova funcionalidade de cliente, novos casos de uso e contratos podem ser criados sem alterar os existentes.
- **L - Liskov Substitution Principle**: As implementações na infraestrutura (como o repositório JPA) podem ser substituídas por qualquer outra tecnologia de banco de dados desde que respeitem a interface `CustomerRepository`.
- **I - Interface Segregation Principle**: Em vez de uma interface genérica e gigante de "Serviço de Cliente", temos contratos pequenos e específicos na aplicação (ex: `CreateCustomer`, `UpdateDelivery`, `DeleteCharge`).
- **D - Dependency Inversion Principle**: O módulo depende fortemente de abstrações. Os casos de uso não conhecem o JPA ou o framework Web; eles dependem de contratos (`CustomerRepository`, `EventPublisher`) que são injetados na construção.

## 🏋️‍♂️ Object Calisthenics

O código se esforça para seguir as regras do Object Calisthenics, visando máxima clareza e orientação a objetos:

1. **Apenas um nível de indentação por método**: Métodos de domínio focam em ações diretas e uso de early returns para evitar blocos aninhados profundos.
2. **Não use a palavra-chave `else`**: O código utiliza verificações defensivas (`if`) com retornos precoces, eliminando a necessidade de sentenças `else` e melhorando a legibilidade.
3. **Envolva todos os primitivos e strings em objetos**: Uso extensivo de **Value Objects** (ex: `Name`, `Birth`, `Cpf`, `Contact`, `Phone`). Uma string nunca é apenas uma string se representa um conceito de negócio.
4. **Coleções de Primeira Classe**: As listas de `Delivery`, `Charge` e `Card` são encapsuladas e protegidas dentro da entidade `Customer`. Modificações só ocorrem através de métodos de negócio (ex: `registerDelivery`, `removeDeliveryById`).
5. **Um ponto por linha**: O fluxo de dados respeita a Lei de Demeter, evitando encadeamentos excessivos que quebram o encapsulamento.
6. **Não abrevie**: Nomes de variáveis, métodos e classes são explícitos e revelam a intenção (ex: `changeActivationStatusByAdmin`, `PasswordValidationCheckImpl`).
7. **Nenhuma classe com mais de duas variáveis de instância**: Embora entidades complexas possuam mais campos, o uso de Value Objects (como agrupar `Phone` e `Email` dentro de `Contact`) reduz o inchaço de atributos soltos.
8. **Nenhum getter/setter/propriedade**: Não existem "setters" burros. As atualizações de estado ocorrem através de métodos descritivos de domínio (ex: `updateCustomer`, `updatePassword`), garantindo que o objeto sempre permaneça em um estado válido. As coleções, quando recuperadas, são imutáveis (`Collections.unmodifiableList`).