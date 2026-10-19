# Módulo `shared`

## Responsabilidade e estrutura

Fornece tipos e recursos transversais. Não é um módulo de negócio único; mudanças aqui podem afetar vários módulos.

- `kernel`: tipos compartilhados como `Cpf`, `Email`, `Name`, `Password`, `Price`, `TypeCoin` e `UrlProduct`.
- `config/security`: filtro JWT, serviço de token, carregamento de usuário e configuração de autorização; DTOs de autenticação em `dto`.
- `config/redis`: configuração Redis e serializer.
- `config/cors` e `config/openApi`: CORS e OpenAPI.
- `exception`: exceções e `GlobalExceptionHandler`.
- `util/pagination`: adaptação de parâmetros e resposta paginada.
- `util/passwordencoder`: contratos, serviço de aplicação, configuração e adapter BCrypt.
- `util/uuidconverter`: conversão de UUID.

## Fluxos/casos de uso técnicos

- **Autenticar:** `AuthController` recebe credenciais; `SecurityConfig`, `JwtService`, `JwtAuthFilter` e `CustomUserDetailsService` sustentam a validação e criação do contexto. Consulte os mappings do controller para os endpoints exatos.
- **Tratar erros HTTP:** exceções propagadas são convertidas pela camada global `GlobalExceptionHandler`.
- **Paginar respostas:** controllers usam `PageRequests` e `PageResponse` para adaptar paginação Spring.
- **Hash de senha:** contratos de encoder e adapter BCrypt são expostos à aplicação; não armazenar senha em claro.

## Referências

- [Configuração compartilhada](../../../src/main/java/com/cleancode/ecommerce/shared/config/)
- [Kernel](../../../src/main/java/com/cleancode/ecommerce/shared/kernel/)
- [Tratamento de erros](../../../src/main/java/com/cleancode/ecommerce/shared/exception/)
- [Utilitários](../../../src/main/java/com/cleancode/ecommerce/shared/util/)
- [Autenticação](../../../src/main/java/com/cleancode/ecommerce/user/infra/controller/AuthController.java)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/shared/)

