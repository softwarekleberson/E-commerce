# Módulo Shared (Compartilhado)

Este diretório contém o módulo **Shared** (Compartilhado/Kernel) do sistema Clean Ecommerce. Ele funciona como o **Shared Kernel** no contexto do Domain-Driven Design (DDD), fornecendo blocos fundamentais e estruturas transversais consumidas por todos os demais módulos.

## 🏗️ Arquitetura do Software

O módulo Shared é essencialmente um provedor de infraestrutura, utilitários, tratamento de exceções base e **Value Objects** universais para não ferir o isolamento das outras camadas da **Clean Architecture**:

1. **Kernel / Value Objects**:
   - Contém abstrações puras e conceitos compartilhados como `Email`, `Cpf`, `Name`, `Price`, e `TypeCoin`.
   - Como múltiplos módulos (Customer, Order) utilizam o conceito de "Preço" ou "E-mail", estas lógicas de negócio centralizam-se aqui para evitar repetição de código.

2. **Exception / Config / Util**:
   - **Exception**: Agrupa as hierarquias de exceções comuns e os handlers globais (como o `ControllerAdvice` no Spring) que traduzem erros de negócio em respostas HTTP apropriadas para o cliente.
   - **Dto / Util**: Objetos de transferência padronizados (ex: paginação universal `PageResponse`) e funções utilitárias que não detêm regras de negócio específicas de um único subdomínio.

## 🧩 Padrões de Projeto (Design Patterns)

- **Shared Kernel**: Como padrão arquitetural, é uma biblioteca de suporte mantida no core para garantir que conceitos fundamentais de negócio (como um CPF válido) não sejam recriados diferentemente em cada módulo.
- **Global Error Handler (Chain of Responsibility / Decorator)**: Centraliza a interceptação de erros HTTP vindos de todas as rotas do sistema.

## 🛡️ Princípios SOLID

- **S - Single Responsibility Principle**: Os objetos no `Shared Kernel` possuem validações muito intrínsecas ao seu próprio formato (ex: `Email` sabe validar seu padrão Regex; não faz mais nada).
- **L - Liskov Substitution Principle**: A hierarquia de Exceções Base permite que o handler global intercepte erros de uma família específica sem precisar conhecer todas as subclasses.

## 🏋️‍♂️ Object Calisthenics

- **Envolva primitivos em objetos**: É o grande pilar do pacote `kernel`. Nenhum módulo usa `String` para email ou `String` para nome; todos consomem os **Value Objects** imutáveis e já validados disponibilizados aqui.
- **Nenhum getter/setter cego**: Todos os VOs criados neste módulo são imutáveis após instanciados, preservando a integridade dos dados trafegados entre as camadas de negócio do e-commerce.