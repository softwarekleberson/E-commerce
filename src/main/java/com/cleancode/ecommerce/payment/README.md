# Módulo Payment (Pagamento)

Este diretório contém o módulo de **Payment** (Pagamento) do sistema Clean Ecommerce. Ele é encarregado de processar transações financeiras, realizar a validação de métodos de pagamento, gerenciar integrações com gateways externos de cobrança e manter o registro do histórico de transações de cada pedido.

## 🏗️ Arquitetura do Software

Apoiado na **Clean Architecture** e no **Domain-Driven Design (DDD)**, o módulo é subdividido em camadas estritas para maximizar a segurança e o isolamento:

1. **Domain (`domain`)**:
   - É o core financeiro. Contém entidades cruciais para a liquidação como `Payment`, `Transaction`, entre outras abstrações centrais.
   - Detém **Value Objects** para dados muito sensíveis, operando sempre com valores absolutos validados (ex: quantias monetárias representadas por objetos encapsulados em vez de primitivos flutuantes).
   - Abstrai a maneira que pagamentos são finalizados através de interfaces de Gateway e de Repositório (`PaymentRepository`), assegurando que a lógica não conheça detalhes como "Stripe" ou "PayPal".

2. **Application (`application`)**:
   - Coordena os **Casos de Uso** ligados a fluxos de cobrança (ex: Processamento de pagamento, Estorno/Refund, Confirmação de Transação).
   - Envolve a integração entre diferentes domínios. Um pagamento aprovado emite eventos ou chamadas diretas que podem notificar os módulos de pedido (`Order`) e notificação/eventos (`Event`).
   - Gerencia DTOs para receber requisições do checkout de forma segura, sem expor os modelos internos ricos.

3. **Infrastructure (`infra`)**:
   - É a ponte com o mundo exterior. Contém controladores para interceptar requisições web, sejam elas diretas de clientes ou via "webhooks" de parceiros financeiros.
   - Implementa os `Gateways` concretos que executam requisições HTTP para as operadoras de cartão.
   - Persiste as faturas, transações e logs utilizando conectores JPA/Hibernate configurados nesta camada.

## 🧩 Padrões de Projeto (Design Patterns)

- **Adapter Pattern (Gateways)**: O sistema comunica-se com a adquirente através de um adaptador que implementa a interface do domínio. Trocar a adquirente significa apenas injetar uma nova implementação.
- **Factory Pattern**: A criação de uma transação complexa de pagamento, a partir dos dados crús de entrada, é centralizada para garantir que todo pagamento nasça num estado consistente.
- **State / Strategy Pattern**: Pagamentos transitam entre estados (Pendente, Aprovado, Rejeitado, Estornado). A transição entre estes estados segue regras de políticas restritas para evitar duplas cobranças.

## 🛡️ Princípios SOLID

- **S - Single Responsibility Principle**: Classes com objetivos cristalinos. A classe que faz a persistência no banco (`RepositoryImpl`) não é a mesma classe que faz o POST para a operadora de cartão de crédito.
- **O - Open/Closed Principle**: Novos métodos de pagamento (PIX, Boleto, Crypto) podem ser adicionados criando novas classes ou adapters, mantendo a classe principal `Payment` e seus contratos fechados a quebras.
- **I - Interface Segregation Principle**: O módulo disponibiliza contratos minimalistas. A aplicação não é forçada a depender de um grande "PaymentService" se só precisa verificar um status.
- **D - Dependency Inversion Principle**: O caso de uso principal depende estritamente das interfaces de domínio e não possui nenhum import referenciando serviços concretos da web ou conectores JDBC.

## 🏋️‍♂️ Object Calisthenics

- **Envolva primitivos em objetos**: Cartões, números de conta e valores em dinheiro não são `Strings` ou `Doubles`, são objetos enriquecidos que carregam toda a sua heurística de formatação e segurança.
- **Não use `else`**: Fluxos como a validação e roteamento do pagamento saem rapidamente (early return) se houver uma recusa ou falha sistêmica, evitando a cascata de if/else comuns em integrações.
- **Nenhum getter/setter cego**: Evita a mutabilidade perigosa. O estado de um pagamento só avança através de métodos de domínio que capturam intenções precisas e não por comandos rasos como `.setStatus(APROVADO)`.
- **Coleções de Primeira Classe**: A lista de transações atrelada a uma conta ou faturamento é completamente imutável para a camada consumidora, garantindo integridade máxima em tempo de execução.