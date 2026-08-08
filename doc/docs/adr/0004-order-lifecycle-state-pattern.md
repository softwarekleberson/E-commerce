# ADR-0004: Model order lifecycle with State

- **Status:** Accepted
- **Date:** 2026-08-07

## Context

An order has a lifecycle with rules that depend on its current state. For example, a pending order can be paid, while a shipped order cannot be paid or cancelled.

Scattering these rules across services with `if`/`switch` checks would make the lifecycle harder to maintain.

## Decision

Model lifecycle behavior through `OrderState` and concrete states:

```text
PendingState
ApprovedState
ShipState
CancelState
```

The `Order` delegates `pay()`, `cancel()` and `ship()` to its current state.

## Consequences

### Positive

- State-specific behavior stays close to the state.
- Invalid transitions are explicit.
- Adding a new state can be localized.
- The order aggregate remains responsible for its lifecycle.

### Negative

- More classes are introduced for the state machine.
- State transitions require careful testing.

## Alternatives considered

### Enum + conditional logic

Simpler for a small lifecycle, but less expressive as transition rules grow.

### Workflow engine

Rejected as unnecessary for the current domain and project scope.
