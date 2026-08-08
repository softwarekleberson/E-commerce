

Pular para o conteúdo
Como usar o Gmail com leitores de tela
3 de 31
reademe
Caixa de entrada

kleberson dos santos silva
Anexos
sex., 8 de mai., 15:42 (há 20 horas)
para mim


 8 anexos
  •  Verificados pelo Gmail
# Módulo Users (Usuários e Autenticação)

Este diretório contém o módulo de **Users** (Usuários) do sistema Clean Ecommerce. Ele é o pilar de Segurança, focado essencialmente em gerenciar a autenticação (Login), autorização, controle de acesso e tokens JWT, fornecendo infraestrutura segura para a aplicação.

## 🏗️ Arquitetura do Software

Apoiado na **Clean Architecture** e projetado para integrar-se sem fricções na infraestrutura da aplicação:

1. **Domain (`domain`)**:
   - Gerencia a entidade focada em credenciais e papéis (`Role`), geralmente referenciando a essência da autenticação, isolada dos detalhes ricos da entidade `Customer` (que lida com entregas, carrinhos, etc).

2. **Application (`application`)**:
   - Os Casos de Uso incluem login, validação de token e registro inicial do perfil de acesso.
   - Contém serviços ou interfaces focados na criptografia de senhas e geração/verificação de Tokens de Autorização (JWT).

3. **Infrastructure (`infra`)**:
   - Fortemente integrada ao **Spring Security**.
   - Gerencia os filtros Web (Filters) de segurança para checar se uma rota exige permissão de ADMIN, USER, etc.
   - Contém os Controllers de Autenticação (`AuthController`) e os repositórios que comunicam com a base de dados para checar login e senha.

## 🧩 Padrões de Projeto (Design Patterns)

- **Filter Chain / Interceptor Pattern**: A infraestrutura do Spring Security avalia sucessivamente os tokens através de uma cadeia de filtros configurada neste módulo, interceptando as requisições antes que atinjam as áreas restritas.
- **Facade**: Componentes de geração e validação de Tokens JWT agem como uma fachada, escondendo a complexidade de algoritmos de assinatura digital dos controladores de domínio.

## 🛡️ Princípios SOLID

- **S - Single Responsibility Principle**: O módulo `Users` cuida puramente do aspecto de acesso e segurança, não misturando lógicas de venda de produtos ou fretes.
- **D - Dependency Inversion Principle**: Ferramentas críticas (como `BCrypt` para hashing de senha ou a biblioteca JWT) não vazam para a regra de negócio. Elas são injetadas através de portas (interfaces) bem desenhadas e resolvidas na camada de infraestrutura.

## 🏋️‍♂️ Object Calisthenics

- **Não use `else`**: Nos filtros de verificação de token, a validação funciona na base do *Early Return* — se não houver cabeçalho de Autorização, a cadeia encerra.
- **Envolva primitivos em Objetos**: Tokens não são apenas *Strings* soltas fluindo na memória. A lógica envelopa esses tokens em estruturas ou contextos de Segurança adequados, garantindo confiabilidade no ciclo de vida de uma requisição.
USER.md
Exibindo USER.md.