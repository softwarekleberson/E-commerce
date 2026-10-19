# Módulo `replacement`

## Responsabilidade e estrutura

Gerencia solicitações de troca/substituição ligadas a itens de pedido. `application/dto` representa criação e listagem; `application/service` busca pedido e valor unitário; `application/usecase` contém operações; `domain` contém `Replacement`, status, motivo, quantidade, explicação, identificador, erro e contrato de repositório; `infra` contém controller, configuração, gateway, mapper e persistência (diretório grafado `persistece`).

## Casos de uso identificados

- Criar solicitação: `CreateReplacementImp`.
- Listar solicitações abertas: `ListReplacementImpl`.
- Aceitar solicitação: `AcceptReplacementImp`.
- Negar solicitação: `NegateReplacementImp`.

`ControllerReplacement` é a entrada HTTP. Os fluxos de revisão também participam do módulo `event` (`ReplacementEvent` e eventos de revisão), que pode coordenar a atualização de estado do pedido ou emissão de voucher. Verificar listeners e configuração para ordem e falhas.

## Referências

- [Controller](../../../src/main/java/com/cleancode/ecommerce/replacement/infra/controller/ControllerReplacement.java)
- [Use cases](../../../src/main/java/com/cleancode/ecommerce/replacement/application/usecase/)
- [Domínio](../../../src/main/java/com/cleancode/ecommerce/replacement/domain/)
- [Eventos relacionados](../../../src/main/java/com/cleancode/ecommerce/event/replacement/)
- [Testes](../../../src/test/java/com/cleancode/ecommerce/replacement/)

