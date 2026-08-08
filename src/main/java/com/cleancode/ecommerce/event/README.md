# Módulo Event (Eventos)

Este diretório contém o módulo de **Event** (Eventos) do sistema Clean Ecommerce. Diferente dos módulos de negócio focados em domínio rico (como Customer ou Order), o módulo Event atua primariamente na camada de infraestrutura e aplicação transversal, facilitando a mensageria e o baixo acoplamento entre os diversos módulos do ecossistema.

## 🏗️ Arquitetura do Software

A estrutura foi modelada para ser o tecido conector da **Clean Architecture**:

1. **Domain (`domain`)**:
   - Fornece contratos (`EventPublisher`, `DomainEvent`) que os outros domínios da aplicação podem implementar ou consumir. 
   - Ao usar eventos de domínio, garantimos que um módulo (ex: `Customer`) possa notificar que algo ocorreu (ex: "Novo Cliente Registrado") sem ter que conhecer ou injetar a dependência de quem vai consumir a informação (ex: serviço de envio de email de boas-vindas).

2. **Application / Infraestrutura**:
   - É responsável pela implementação técnica de despachar e ouvir eventos.
   - Pode conter os Listeners (ouvintes) e a integração com mensageria assíncrona (como RabbitMQ, Kafka, ou o ApplicationEventPublisher do próprio Spring, dependendo da configuração).

## 🧩 Padrões de Projeto (Design Patterns)

- **Publisher-Subscriber (Observer)**: Padrão primário deste módulo. Módulos geram eventos (Publishers) e outros módulos ou serviços de infraestrutura se inscrevem para processar esses eventos em paralelo (Subscribers).
- **Mediator**: Age como um mediador central, roteando as mensagens para os interessados sem que eles tenham que se conhecer explicitamente.

## 🛡️ Princípios SOLID

- **O - Open/Closed Principle**: Novos "ouvintes" (Listeners) podem ser facilmente acoplados ao sistema para reagir a um evento sem necessidade de modificar a classe que gerou o evento.
- **D - Dependency Inversion Principle**: O core da aplicação depende apenas das interfaces abstratas (ex: `EventPublisher`), deixando os detalhes técnicos do barramento de eventos (Spring Events, Kafka) para este módulo de infraestrutura resolver.

## 🏋️‍♂️ Object Calisthenics

- Devido à sua natureza de mensageria, o código mantém assinaturas simples: **apenas um nível de indentação** e **encapsulamento de primitivos**. Os dados transferidos via eventos são frequentemente encapsulados em classes de "Eventos" altamente coesas e imutáveis.