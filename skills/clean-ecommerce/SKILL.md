---
name: clean-ecommerce
description: Apply the Clean Ecommerce project's architecture and conventions when changing its Java/Spring Boot code, tests, persistence, or API.
metadata:
  short-description: Work in the Clean Ecommerce codebase
---

# Clean Ecommerce

Use this skill for implementation and maintenance work in this repository. Treat the code as the source of truth: architecture documentation describes intent, while individual modules have some naming and structure variations.

## Como o projeto está construído

- É uma única aplicação Spring Boot/Maven, não um conjunto de microserviços. A classe de entrada e os beans são descobertos a partir do pacote `com.cleancode.ecommerce`.
- Os módulos são agrupados por capacidade de negócio: `customer`, `product`, `cart`, `stock`, `order`, `payment`, `promotional`, `replacement`, `user` e `adm`. `event` contém eventos locais usados entre módulos; `shared` reúne configuração e componentes transversais.
- O fluxo comum é controller/adaptador de entrada → caso de uso ou serviço de aplicação → domínio e contratos → gateway/adaptador de infraestrutura → banco ou serviço externo. Isso é uma orientação, não uma estrutura uniforme: por exemplo, `promotional` usa `usecase/`, `user` é mais enxuto e alguns pacotes têm grafias históricas como `persistencia` ou `persistece`.
- As interfaces de persistência costumam ficar em `domain/repository`; implementações e mapeamentos JPA ficam em `infra/gateway`, `infra/mapper` e `infra/persistence` (com variações). Controllers e DTOs HTTP também ficam em `infra` nos módulos que os possuem.
- A aplicação usa MySQL com Spring Data JPA e migrações Flyway, Redis para cache quando configurado, Spring Security/JWT para autenticação e eventos Spring no mesmo processo. O módulo `payment` inclui um adaptador fake; não presuma integração com um provedor de pagamento real.
- A configuração compartilhada está em `shared/config` (segurança, CORS, OpenAPI e Redis), `shared/exception`, `shared/kernel` e `shared/util`. Configuração operacional adicional fica em `src/main/resources/application.properties` e `docker-compose.yml`.
- Consulte [`doc/docs/architecture.md`](../../doc/docs/architecture.md) para o mapa do sistema e os fluxos entre módulos. As páginas [`doc/docs/architecture/`](../../doc/docs/architecture/) descrevem módulos e ligam às classes relevantes; os ADRs em [`doc/docs/adr/`](../../doc/docs/adr/) registram decisões pretendidas, que devem ser comparadas com a implementação antes de orientar uma mudança.

## Orient in the repository

- Java source is under `src/main/java/com/cleancode/ecommerce`; tests are under `src/test/java`.
- The project uses Java 25, Spring Boot 3.5, Maven, MySQL, Flyway, Redis, and Spring Security. Prefer the Maven wrapper (`./mvnw`; `mvnw.cmd` on Windows).
- Business code is organized by capability (`customer`, `product`, `cart`, `stock`, `order`, `payment`, `promotional`, `user`, `adm`). Most modules contain `application`, `domain`, and `infra`, but inspect the target module before choosing packages or mirroring its structure.
- The product catalog supports the `Bag`, `Book`, and `Toy` subtypes. Their domain types live under `product/domain/bag`, `books`, and `toy`; subtype DTOs and persistence mappers/entities also have dedicated files or subfolders. `CreateToyDto` creates `Toy`, `ProductDtoFactory` selects `ListToyDto`, and `ToyMapper` maps to `ToyEntity` in `tb_toy` (Flyway migration `V36`). Check `doc/docs/architecture/product.md` and the product module README before extending this subtype flow.
- For architecture decisions and current limitations, consult [`doc/docs/architecture.md`](../../../doc/docs/architecture.md) and relevant ADRs in `doc/docs/adr/`. Do not assume every documented pattern is consistently implemented.

## Preserve boundaries

- Keep business rules and invariants in domain objects and value objects. Keep application use cases focused on coordinating work and module contracts.
- Keep Spring MVC, JPA/Hibernate, Redis, and other technical details in infrastructure. Depend on domain/application contracts at boundaries rather than introducing infrastructure dependencies into the domain.
- Follow neighboring code for existing package names and conventions; avoid broad renames or architecture migrations as part of a feature unless requested.
- Prefer existing domain types for concepts such as IDs, `Price`, `Quantity`, `Email`, and `Cpf`. Check `shared` and the relevant module before introducing another primitive or duplicate type.
- Keep aggregate state protected. Route changes through behavior that preserves invariants rather than adding unrestricted setters or exposing mutable collections.
- Use patterns such as State, Strategy, Factory, and events only where the existing domain problem calls for them. Do not add a pattern for its own sake.
- When a change crosses module boundaries, identify the owning capability and use its existing contracts, gateways, or events. Avoid reaching into another module's persistence implementation.

## Change workflow

1. Read the target module and a comparable implementation, plus any relevant architecture documentation or Flyway migrations.
2. Trace the feature from its entry point through application behavior, domain rules, contracts, and infrastructure. Make the smallest coherent change at those boundaries.
3. For API changes, inspect nearby controllers, request/response DTOs, validation, and exception mapping. For persistence changes, inspect the domain contract, mapper, JPA entity, gateway, and migration history.
4. Add or update focused tests when the task calls for tests. Prefer domain tests for business invariants and application tests for orchestration; follow the repository's existing test style.
5. Report the paths changed and the verification performed. Do not claim a command passed unless it was actually run.

## Commands

- Run the full test suite with `./mvnw test` when testing is requested or needed to verify the change.
- Run `./mvnw verify` when coverage/report generation or the full Maven verification lifecycle is relevant.
- Start local infrastructure with `docker compose up -d` only when the task needs it; integration work may depend on MySQL or Redis.

Do not start services, alter database state, or expand a requested change into unrelated refactoring without a reason tied to the task.
