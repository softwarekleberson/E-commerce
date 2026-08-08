## 🏛️ Architecture

Clean Ecommerce is organized around business capabilities and uses Clean Architecture principles to separate business rules from infrastructure concerns.

```text
Customer · Product · Cart · Order · Payment · Stock · Promotional
                              │
                              ▼
                       Application / Use Cases
                              │
                              ▼
                         Domain / Rules
                              │
                       Dependency Inversion
                              │
                              ▼
                  Infrastructure / Adapters
                    JPA · Redis · Security · Events
```

### Architecture documentation

- 📐 [Architecture overview](docs/architecture.md)
- 🧠 [Architecture Decision Records](docs/adr/README.md)
