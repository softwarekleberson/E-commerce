# Módulo Product (Produto)

Este diretório contém o módulo de **Product** (Produto) do sistema Clean Ecommerce. Ele é responsável pelo catálogo de produtos, gerenciando a criação, alteração de status (ativo/inativo), categorias (como bolsas, livros), mídias (fotos/vídeos), precificação e dimensões físicas.

## 🏗️ Arquitetura do Software

O módulo foi construído respeitando as regras da **Clean Architecture** e modelado usando **Domain-Driven Design (DDD)**:

1. **Domain (`domain`)**:
   - É o núcleo puro da aplicação. Contém a entidade agregadora `Product`, que orquestra e mantém a consistência dos dados do produto.
   - Possui subpacotes (`bag`, `books`) indicando que o sistema trata diferentes categorias de produtos que podem possuir características únicas.
   - Está repleto de **Value Objects** que validam regras específicas (ex: `Pricing` não pode ser negativo, `Dimension` deve ter medidas válidas, `Midia` armazena URLs).
   - O domínio não enxerga nenhuma tecnologia de persistência, apenas define a interface `ProductRepository`.

2. **Application (`application`)**:
   - Casos de uso (`useCase`) comandam o fluxo de controle, como adicionar um produto, atualizar detalhes ou inativar/ativar produtos.
   - Trabalha estritamente com **DTOs** (`dto`), recebendo dados do exterior, chamando os métodos corretos do domínio e devolvendo dados modelados sem expor o domínio em si.

3. **Infrastructure (`infra`)**:
   - Camada que conecta a aplicação com o framework Web e Banco de Dados.
   - Contém os Controladores (`controller`) que escutam as requisições HTTP REST.
   - Fornece as integrações de JPA no pacote de persistência (`persistence` e `gateways`).

## 🧩 Padrões de Projeto (Design Patterns)

- **Aggregate / Entity**: A entidade `Product` não é um aglomerado de campos sem sentido, ela é a Raiz de Agregação (Aggregate Root). Operações como "atualizar preço" são métodos de `Product` que mudam o estado de `Pricing`.
- **Factory Pattern**: Usado de forma conceitual na instanciação complexa de produtos através dos DTOs ou mappers.
- **Strategy / State Pattern**: Evidenciado na classe `ProductStatusPolicy` e enumerações de `Status` (ativo, inativo, pausado), para lidar com o ciclo de vida do produto.
- **Repository Pattern**: Abstração da leitura e gravação, permitindo mockar testes e alterar o banco sem reescrever o sistema.

## 🛡️ Princípios SOLID

- **S - Single Responsibility Principle**: A política de alteração de status (`ProductStatusPolicy`) é separada da entidade principal, garantindo que as regras de mudança de status tenham sua própria classe, mantendo a responsabilidade única.
- **O - Open/Closed Principle**: Novos tipos de produtos (atualmente `bag`, `books`) podem ser adicionados criando novos pacotes/subclasses sem alterar o núcleo do `Product`.
- **L - Liskov Substitution Principle**: A implementação da interface do repositório pode ser substituída sem quebrar o módulo, permitindo a transição entre MongoDB, Postgres ou em-memória.
- **D - Dependency Inversion Principle**: Controladores e Casos de Uso sempre dependem de interfaces (`ProductRepository`, interfaces de casos de uso), nunca de classes concretas de banco de dados ou da web.

## 🏋️‍♂️ Object Calisthenics

- **Não use Else**: Evitado através de retornos imediatos. Verificações de nulo ou valores inválidos lançam exceptions no topo dos métodos de domínio.
- **Envolva todos os primitivos**: Preço (`Pricing`), Descrição (`Description`), Marca (`Brand`), Dimensões (`Dimension`) são objetos. Uma marca não é uma simples `String`, é um `Brand` que pode carregar lógicas de validação.
- **Nenhum getter/setter cego**: Não é possível simplesmente chamar `product.setStatus("INACTIVE")`. O código utiliza métodos orientados a intenção (ex: `activate()`, `deactivate()`), que podem checar políticas antes de ocorrer a mudança.
- **Classes e Entidades Pequenas**: Como os campos do produto foram agrupados em objetos menores (Dimensões engloba altura, largura, peso), a classe `Product` tem poucas variáveis de instância, deixando o design limpo.