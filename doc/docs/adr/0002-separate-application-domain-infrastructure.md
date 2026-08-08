# ADR-0002: Separate application, domain and infrastructure

- **Status:** Accepted
- **Date:** 2026-08-07

## Context

Business rules should not be tightly coupled to HTTP, JPA, Hibernate, Redis or other implementation details.

The project explicitly studies Clean Architecture and dependency inversion.

## Decision

Within business modules, separate concerns into:

```text
application/
domain/
infra/
```

- **Domain:** entities, value objects, business rules, domain exceptions and contracts.
- **Application:** use cases, orchestration and application DTOs/services.
- **Infrastructure:** controllers, persistence, gateways, mappers and framework configuration.

## Consequences

### Positive

- Business rules are easier to test without infrastructure.
- Technical details can be replaced behind contracts.
- Use cases have a clear orchestration responsibility.
- Controllers remain thin adapters.

### Negative

- More classes and mappings are required.
- Simple CRUD operations may feel verbose.
- Developers must respect dependency direction.

## Alternatives considered

### Anemic layered architecture

Rejected because it would move too much behavior into services and weaken the domain model.

### Framework-first architecture

Rejected because the project goal is to keep business concepts central rather than letting Spring/JPA define the domain structure.
