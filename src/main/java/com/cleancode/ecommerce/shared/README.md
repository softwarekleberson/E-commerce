# Módulo `shared`

## Responsabilidade

Oferece tipos e infraestrutura transversal usados pelos módulos de negócio. Nem todo conteúdo é um shared kernel de domínio: o pacote também contém configuração Spring e utilitários técnicos.

## Estrutura e arquitetura

- `kernel/`: tipos compartilhados (`Cpf`, `Email`, `Name`, `Password`, `Price`, `TypeCoin`, `UrlProduct`).
- `config/cors/`, `config/openApi/`: CORS e OpenAPI.
- `config/redis/`: configuração de Redis e serializer.
- `config/security/`: filtro JWT, serviço de token, serviço de detalhes do usuário e configuração de segurança; `dto/` contém DTOs de autenticação.
- `exception/`: erros compartilhados e `GlobalExceptionHandler`.
- `util/pagination/controller/`: adaptação de paginação e resposta paginada.
- `util/passwordencoder/`: contrato/serviço de aplicação, configuração e adapter BCrypt.
- `util/uuidconverter/`: conversão de UUID.

## Fluxos técnicos identificados

- Autenticação é recebida em `user/infra/controller/AuthController` e apoiada pelos componentes JWT em `config/security/`.
- Exceções são traduzidas globalmente por `GlobalExceptionHandler`.
- Paginação usa `PageRequests` e `PageResponse`.
- Codificação de senha é abstraída por contratos e implementada com BCrypt no adapter de infraestrutura.

Os componentes devem permanecer genéricos; regras específicas de cliente, produto ou pagamento pertencem aos respectivos módulos.

## Referências

- [Configuração](config/)
- [Kernel compartilhado](kernel/)
- [Exceções](exception/)
- [Utilitários](util/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/shared.md)
