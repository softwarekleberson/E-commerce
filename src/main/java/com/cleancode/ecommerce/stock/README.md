# Módulo `stock`

## Responsabilidade

Controla estoque, entradas e saídas de produto e reservas consumidas pelo carrinho e pelo checkout.

## Estrutura e arquitetura

- `application/dto/`: dados de criação e listagem de estoque/entradas.
- `application/usecase/`: criar estoque e registrar entrada; `contract/` contém as interfaces desses casos de uso.
- `domain/`: agregado `Stock`, quantidade, fornecedor, qualidade, preço de compra, exceções e contrato `StockRepository`.
- `domain/productinput/`, `productoutput/`: registros de entrada e saída.
- `domain/reservation/`: reserva, identificador e status da reserva.
- `infra/config/`, `controller/`, `gateway/`, `mapper/`, `persistence/`: composição, HTTP, adapter JPA, conversores e entidades.

## Casos de uso e colaborações

- Criar estoque: `CreateStockImpl`.
- Adicionar entrada de produto: `AddProductStockImpl`.
- Consultar/reservar/liberar estoque: integrações do carrinho usam o módulo por meio de seus contratos/repositório; não existe uma classe de caso de uso nomeada para cada operação em `application/usecase/`.
- Confirmar reserva no checkout: `CheckoutImpl` busca estoque por ID de reserva, chama `Stock.confirmOrder(productId, reservationId)` e salva o agregado.

`Stock` é o ponto de consistência das operações de estoque. Regras de expiração e compensação da reserva dependem dos fluxos chamadores e não devem ser presumidas pelo nome do modelo.

## Referências

- [Controller](infra/controller/StockController.java)
- [Use cases](application/usecase/)
- [Domínio](domain/)
- [Infraestrutura](infra/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/stock.md)
