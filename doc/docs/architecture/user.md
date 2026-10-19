# Módulo `user`

## Responsabilidade e estrutura

Pequeno módulo de autenticação: `domain` contém `User` e `UserId`; `infra/controller` contém `AuthController`; `infra/persistence` contém `UserEntity`. A implementação de autenticação JWT e hash de senha está principalmente em [shared](shared.md), e não dentro deste módulo.

## Fluxo identificado

`AuthController` expõe o ponto de entrada HTTP para autenticação. O modelo de usuário e sua entidade persistida atendem a essa borda. O carregamento de detalhes, criação/validação de JWT e autorização são realizados pelos componentes em `shared/config/security`. Para endpoints e regras de credenciais, consulte diretamente o controller e os serviços de segurança; esta página não presume registro de usuário porque nenhum caso de uso de cadastro está presente na árvore do módulo.

## Referências

- [AuthController](../../../src/main/java/com/cleancode/ecommerce/user/infra/controller/AuthController.java)
- [Modelo](../../../src/main/java/com/cleancode/ecommerce/user/domain/)
- [Entidade persistida](../../../src/main/java/com/cleancode/ecommerce/user/infra/persistence/UserEntity.java)
- [Segurança compartilhada](../../../src/main/java/com/cleancode/ecommerce/shared/config/security/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/user/)

