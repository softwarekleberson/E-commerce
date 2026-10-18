<div align="center">

# 🛒 Clean Ecommerce

### API de e-commerce em Java e Spring Boot, organizada por capacidades de negócio.

[![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.0-6DB33F?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7-DC382D?style=for-the-badge&logo=redis)](https://redis.io/)

**Clean Architecture · DDD · SOLID · Spring**

</div>

## Sobre

O Clean Ecommerce é uma API backend para operações de comércio eletrônico. O projeto explora modelagem de domínio, separação de responsabilidades e integração entre funcionalidades como clientes, catálogo, carrinho, pedidos, pagamentos e estoque.

A aplicação está organizada em módulos Java sob `com.cleancode.ecommerce`. Cada módulo pode ter uma estrutura própria; em geral, controllers recebem requisições HTTP, a aplicação coordena os casos de uso e o domínio concentra modelos e regras. Adaptadores de infraestrutura conectam esses componentes a banco de dados, cache e serviços do Spring.

## Módulos

| Módulo | Responsabilidade geral |
|---|---|
| `adm` | Operações administrativas, incluindo pedidos, clientes, vouchers e relatórios |
| `cart` | Carrinho e itens |
| `customer` | Cadastro e dados do cliente |
| `event` | Eventos locais usados na comunicação entre funcionalidades |
| `order` | Pedidos e ciclo de vida |
| `payment` | Checkout e processamento de pagamentos |
| `product` | Catálogo de produtos |
| `promotional` | Vouchers e descontos |
| `replacement` | Trocas e substituições |
| `shared` | Configuração, exceções, kernel compartilhado e utilitários |
| `stock` | Estoque, movimentações e reservas |
| `user` | Usuários e autenticação |

O [guia de arquitetura](doc/docs/architecture.md) descreve a implementação e contém links para a documentação de cada módulo. As [decisões arquiteturais](doc/docs/adr/README.md) registram as decisões de projeto.

## Tecnologias e infraestrutura

- Java 25 e Spring Boot 3.5;
- Spring MVC, validação, Spring Security e JWT;
- Spring Data JPA, Hibernate, MySQL 8 e Flyway;
- Spring Data Redis e Spring Cache;
- Actuator, Micrometer e Prometheus;
- SpringDoc OpenAPI;
- Maven Wrapper, JUnit e JaCoCo;
- Docker Compose para MySQL, Redis, Prometheus, Grafana, exporters e Adminer.

## Executar localmente

### Pré-requisitos

- JDK 25;
- Docker com o plugin Docker Compose;
- Maven Wrapper incluído no repositório.

### Configurar ambiente

Copie `.env.example` para `.env` e defina os valores exigidos para MySQL, Grafana e assinatura JWT. Não compartilhe credenciais reais.

```bash
cp .env.example .env
docker compose up -d
```

Inicie a aplicação em outro terminal:

```bash
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`.

As configurações da aplicação ficam em `src/main/resources/application.properties`. O Compose publica MySQL em `3307`, Redis em `6379`, Prometheus em `9090`, Grafana em `3000` e Adminer em `8085`.

## API e documentação

Com a aplicação em execução, a interface Swagger UI fica em `http://localhost:8080/swagger-ui/index.html` e o documento OpenAPI em `http://localhost:8080/v3/api-docs`.

## Testes e cobertura

Execute os testes com:

```bash
./mvnw test
```

Para gerar o relatório JaCoCo:

```bash
./mvnw verify
```

O relatório é escrito em `target/site/jacoco/`.

## Estrutura do repositório

```text
src/main/java/com/cleancode/ecommerce/  Código da aplicação, por módulo
src/main/resources/                     Configurações e migrações Flyway
src/test/                               Testes automatizados
doc/docs/architecture.md                Índice e visão da arquitetura
doc/docs/adr/                           Decisões arquiteturais
docker-compose.yml                      Serviços locais de apoio
```

## Autor

**Kleberson Santos**

[GitHub](https://github.com/softwarekleberson) · [LinkedIn](http://linkedin.com/in/klebersondossantossilva01)
