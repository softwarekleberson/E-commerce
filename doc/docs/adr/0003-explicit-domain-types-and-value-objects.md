# ADR-0003: Use explicit domain types and Value Objects

- **Status:** Accepted
- **Date:** 2026-08-07

## Context

Primitive values such as strings, numbers and UUIDs can hide domain meaning and allow invalid values to travel through the application.

The codebase contains concepts such as `Email`, `Cpf`, `Name`, `Price`, `Password` and domain-specific identifiers.

## Decision

Represent important business concepts with dedicated domain types/value objects when they have their own meaning, validation or invariant.

Examples:

```text
Email
Cpf
Name
Price
Password
ProductId
OrderId
CustomerId
PaymentId
StockId
VoucherId
ReservationId
```

## Consequences

### Positive

- Business meaning is visible in method signatures.
- Validation can live near the concept it protects.
- Primitive obsession is reduced.
- Domain rules become easier to reason about.

### Negative

- More types and constructors are required.
- Persistence mapping becomes more involved.
- Not every primitive requires a dedicated type; judgment is still needed.

## Alternatives considered

### Use primitives everywhere

Rejected for concepts with meaningful validation and identity.

### Create Value Objects for every field

Rejected because excessive wrapping would increase complexity without adding domain value.
