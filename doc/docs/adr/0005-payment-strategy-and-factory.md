# ADR-0005: Select payment behavior with Strategy + Factory

- **Status:** Accepted
- **Date:** 2026-08-07

## Context

The system supports different payment behaviors:

```text
CARD
TWO_CARDS
VOUCHER
VOUCHER_CARD
```

Each method has different validation and authorization rules.

## Decision

Represent each payment algorithm through the `PaymentMethod` contract and use `PaymentMethodFactoryImpl` to select the implementation by `TypePayment`.

```text
PaymentMethod
├── CardPayment
├── TwoCardsPayment
├── VoucherPayment
└── CardAndCouponPayment
```

## Consequences

### Positive

- Payment algorithms are isolated.
- `CheckoutImpl` does not need to contain a large payment conditional.
- New payment strategies can be added behind the same contract.
- Each strategy can be tested independently.

### Negative

- More objects are required.
- The factory needs to be kept synchronized with available strategies/configuration.

## Alternatives considered

### Large conditional in checkout

Rejected because it would increase coupling and make the checkout use case harder to evolve.

### Separate service for each payment type without a common contract

Rejected because a common strategy abstraction makes the selection and orchestration model clearer.
