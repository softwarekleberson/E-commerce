# Módulo `user`

## Responsabilidade

Contém o modelo mínimo de usuário usado no fluxo de autenticação. A segurança JWT e a codificação de senha estão implementadas principalmente em `shared`.

## Estrutura e arquitetura

- `domain/`: `User` e `UserId`.
- `infra/controller/`: `AuthController`, ponto de entrada HTTP para autenticação.
- `infra/persistence/`: `UserEntity`, representação persistida do usuário.

Não existem pastas `application/`, casos de uso de cadastro ou contratos de repositório neste módulo atualmente. Não inferir registro de usuário ou papéis que não estejam na implementação.

## Fluxo relacionado

O controller de autenticação atende a borda HTTP; componentes compartilhados carregam os detalhes do usuário, validam/geram JWT, aplicam filtros e configuram autorização. Consulte os mappings do controller para rotas e comportamento de resposta atuais.

## Referências

- [AuthController](infra/controller/AuthController.java)
- [Modelo de domínio](domain/)
- [Entidade persistida](infra/persistence/UserEntity.java)
- [Segurança compartilhada](../shared/config/security/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/user.md)
