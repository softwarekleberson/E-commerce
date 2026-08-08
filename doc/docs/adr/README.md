# Architecture Decision Records

This directory records the architectural decisions that shape Clean Ecommerce.

These ADRs are intentionally concise. They explain **why** a structural decision exists, not only **what** the code currently does.

> The ADRs document the current implementation and the architectural intent being formalized for the project. They should be updated when a decision is deliberately changed.

## Index

| ADR | Decision | Status |
|---|---|---|
| [0001](0001-organize-by-business-capability.md) | Organize code by business capability | Accepted |
| [0002](0002-separate-application-domain-infrastructure.md) | Separate application, domain and infrastructure | Accepted |
| [0003](0003-explicit-domain-types-and-value-objects.md) | Use explicit domain types and Value Objects | Accepted |
| [0004](0004-order-lifecycle-state-pattern.md) | Model order lifecycle with State | Accepted |
| [0005](0005-payment-strategy-and-factory.md) | Select payment behavior with Strategy + Factory | Accepted |
| [0006](0006-repository-and-gateway-contracts.md) | Depend on repository/gateway contracts | Accepted |
| [0007](0007-in-process-domain-events.md) | Use in-process application events | Accepted |
