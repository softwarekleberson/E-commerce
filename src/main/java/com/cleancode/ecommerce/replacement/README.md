# Módulo `replacement`

## Responsabilidade

Gerencia solicitações de substituição/troca associadas a pedidos, incluindo abertura, consulta e decisão administrativa.

## Estrutura e arquitetura

- `application/dto/`: modelos de criação e listagem de solicitações abertas.
- `application/service/`: consultas auxiliares de pedido e valor unitário do produto.
- `application/usecase/`: criação, listagem, aceite e negativa; `contract/` define contratos.
- `domain/`: agregado `Replacement`, status, motivo, explicação, quantidade, identificador, exceção e `ReplacementRepository`.
- `infra/config/`, `controller/`, `gateway/`, `mapper/`, `persistece/`: composição, entrada HTTP, adapter, conversão e entidades. `persistece` é a grafia existente no pacote.

## Casos de uso encontrados

- Criar solicitação: `CreateReplacementImp`.
- Listar solicitações: `ListReplacementImpl`.
- Aceitar solicitação: `AcceptReplacementImp`.
- Negar solicitação: `NegateReplacementImp`.

`ControllerReplacement` é a entrada REST. Reações à decisão também atravessam eventos de `event/replacement` e `event/ExchangeRequestAfterReview`; listeners podem atualizar pedido ou solicitar voucher. A ordem e o tratamento de falhas dependem das implementações e configuração dos listeners.

## Referências

- [Controller](infra/controller/ControllerReplacement.java)
- [Use cases](application/usecase/)
- [Domínio](domain/)
- [Eventos relacionados](../event/replacement/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/replacement.md)
