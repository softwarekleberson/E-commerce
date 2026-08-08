# ADR-0001: Organize code by business capability

- **Status:** Accepted
- **Date:** 2026-08-07

## Context

A traditional layered structure such as `controllers/`, `services/`, `repositories/` and `entities/` can make a growing e-commerce codebase harder to navigate by business capability.

The project contains distinct concepts such as customer, product, cart, order, payment, stock and promotional rules.

## Decision

Organize the application primarily by **business capability/module**:

```text
customer/
product/
cart/
order/
payment/
stock/
promotional/
adm/
user/
event/
shared/
```

Each module owns the application and domain concerns related to that capability and exposes only the abstractions required by other modules.

## Consequences

### Positive

- Business concepts are easier to locate.
- Changes are more naturally scoped to a module.
- The package structure communicates the domain model.
- It supports future extraction of bounded capabilities if that becomes useful.

### Negative

- Developers must understand the business boundaries.
- Cross-module workflows can involve several repository/contracts.
- Some shared concepts need careful ownership to avoid a "shared everything" package.

## Alternatives considered

### Technical layers across the whole application

Simpler initially, but less aligned with the domain and more likely to create large global layers.

### Microservices per business module

Rejected for the current scope. The project is a modular monolith; introducing network boundaries would add operational complexity without a current business requirement.
