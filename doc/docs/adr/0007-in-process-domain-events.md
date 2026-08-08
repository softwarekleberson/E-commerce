# ADR-0007: Use in-process application events

- **Status:** Accepted
- **Date:** 2026-08-07

## Context

Some product and stock reactions do not need to be executed directly by the component that produces the event.

The project already contains an `EventPublisher` abstraction and Spring event listeners.

## Decision

Use Spring's in-process `ApplicationEventPublisher` behind the project's `EventPublisher` abstraction.

Current listeners include:

```text
ActiveProductEventListener
CreatedStockEventListener
SellingPriceEventListener
```

## Consequences

### Positive

- Producers are decoupled from internal listeners.
- Additional reactions can be added without changing the producer's direct dependencies.
- The implementation remains simple for a modular monolith.

### Negative

- Events are local to the application process.
- There is no durable broker or cross-service delivery guarantee.
- Transactional/event consistency needs additional design if the system evolves toward distributed services.

## Alternatives considered

### Kafka/RabbitMQ/etc.

Not justified for the current modular-monolith scope. A broker can be introduced later if a distributed architecture creates a concrete requirement for durable asynchronous messaging.
