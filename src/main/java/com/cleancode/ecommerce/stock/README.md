# Módulo Stock (Estoque)

Este diretório contém o módulo de **Stock** (Estoque) do sistema Clean Ecommerce. Ele é vital para o sistema, garantindo a disponibilidade real dos produtos para venda e evitando os problemas clássicos de concorrência onde dois clientes compram o último item ao mesmo tempo.

## 🏗️ Arquitetura do Software

Mantendo a coesão de todo o ecossistema, este módulo aplica rigorosamente a **Clean Architecture** em paralelo com o **Domain-Driven Design (DDD)**:

1. **Domain (`domain`)**:
   - Isolado do mundo exterior. Possui a entidade agregadora base `Stock`, a qual rastreia a quantidade disponível de produtos.
   - Centraliza regras como "reserva temporária de produtos" (ex: quando um item vai para o carrinho) usando o conceito de `Reservation` e `ReservationId`.
   - Utiliza Value Objects importantes, principalmente a classe `Quantity`, para garantir que os valores nunca fiquem negativos indevidamente.
   - Estabelece as abstrações dos repositórios, como `StockRepository`, para consultar e gerenciar as fatias do estoque.

2. **Application (`application`)**:
   - Contém a inteligência orquestradora. Casos de Uso como aprovação de saída de estoque, reservas temporárias, exclusão ou atualização de quantidades.
   - Atende requisições de outros módulos. Por exemplo, quando o módulo `Order` tenta adicionar um produto ao carrinho, a verificação e reserva acontecem orquestradas pelos Casos de Uso do módulo `Stock` (ex: `ValidateProductHasStock`).

3. **Infrastructure (`infra`)**:
   - Contém a camada web (Controllers) e persistência de dados.
   - Converte e salva entidades de domínio no banco de dados usando Spring Data JPA, lidando também com *locks* ou controle transacional quando necessário para evitar problemas de concorrência.

## 🧩 Padrões de Projeto (Design Patterns)

- **Aggregate Root**: `Stock` funciona como uma barreira de consistência. Não se altera uma `Reservation` diretamente; toda alocação passa pela raiz, que subtrai da quantidade livre total.
- **Dependency Injection**: Casos de uso do estoque e controladores interagem entre si usando abstrações injetadas nos construtores, maximizando a possibilidade de criar testes de unidade.
- **Repository Pattern**: Centraliza os métodos de busca do banco de dados (ex: `findById`, `save`), escondendo a complexidade do ORM da camada de negócios.

## 🛡️ Princípios SOLID

- **S - Single Responsibility Principle**: O módulo de estoque tem a responsabilidade exclusiva de controlar as disponibilidades e reservas. Ele não sabe o preço do produto nem o nome do cliente.
- **O - Open/Closed Principle**: Novos fluxos de processamento (ex: estoque danificado, retorno de mercadoria) podem ser facilmente acoplados implementando novos casos de uso, sem a necessidade de modificar os fluxos que realizam reservas de venda.
- **I - Interface Segregation**: O módulo utiliza contratos bem definidos para que as classes clientes consumam apenas as assinaturas de que realmente precisam (ex: segregar consultas de atualizações).
- **D - Dependency Inversion Principle**: O acesso a dados não depende do Framework Spring diretamente no núcleo. `StockRepository` é apenas uma interface que a infraestrutura se responsabiliza por implementar.

## 🏋️‍♂️ Object Calisthenics

- **Não use `else`**: Validações de limites (ex: quando a reserva solicitada excede o limite disponível) lançam exceções como `IllegalDomainException` rapidamente com *early return*, dispensando blocos aninhados e dificultosos de ler.
- **Envolva Primitivos em Objetos**: Quantidade de estoque não é um tipo primitivo `int` ou `Long`. É uma classe `Quantity`, que internamente impede que instanciem lixos ou números negativos.
- **Nenhum getter/setter que quebre encapsulamento**: A manipulação da quantidade não é realizada através de algo trivial como `estoque.setQuantidade()`. Ela é feita por meio de intenções negociais claras (ex: `reserve()`, `liberate()`), que disparam a lógica de cálculo das sobras internamente, mantendo o controle centralizado.
- **Não abrevie**: As operações que lidam com itens e exceções são sempre verbosas (ex: `ValidateProductHasStock`), revelando explicitamente a intenção do código.