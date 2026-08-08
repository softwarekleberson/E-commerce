# ADR-0006: Depend on repository and gateway contracts

- **Status:** Accepted
- **Date:** 2026-08-07

## Context

The domain and application layers need persistence or external-service capabilities, but should not know the concrete technology used to provide them.

The project uses JPA/Hibernate/MySQL and also abstracts payment gateway behavior.

## Decision

Define contracts in inner layers and provide implementations in infrastructure/application adapters.

Examples include:

```text
CustomerRepository
CartRepository
OrderRepository
PaymentRepository
ProductRepository
VoucherRepository
StockRepository
PaymentGatewayClient
```

Infrastructure provides concrete adapters such as `*RepositoryJpa` and the fake payment gateway used for local development/testing.

## Consequences

### Positive

- Domain/application code is less coupled to JPA and external services.
- Fake implementations can be used in tests.
- Persistence technology can change with less impact on the domain.

### Negative

- Interfaces and adapters add indirection.
- Mapping and adapter code must be maintained.

## Alternatives considered

### Inject Spring Data repositories directly into domain/application rules

Rejected because it would couple the core logic to framework persistence abstractions.
