# Módulo `cart`

## Responsabilidade

Mantém o carrinho do cliente e coordena integrações com catálogo e estoque para adicionar/alterar/remover produtos e tratar reservas.

## Estrutura e arquitetura

- `application/dtos/input/` e `output/`: modelos de entrada e resposta do carrinho.
- `application/service/`: serviços de integração com catálogo, consulta, remoção, atualização e reserva; `contract/` define as portas e `dto/` os resultados dessas integrações.
- `application/usecase/`: casos de uso e `contract/` com interfaces de entrada.
- `domain/`: `Cart`, `CartItem`, identificadores, exceção e `CartRepository`.
- `infra/config/`: composição do módulo; `controller/`: API HTTP; `gateway/`: adapter JPA; `mapper/`: conversão entre modelos; `persistence/`: entidades JPA.

## Casos de uso e entradas

- Adicionar produto — `AddProductToCartImpl`, `POST /customer/cart`.
- Consultar carrinho — `ListCartImpl`, `GET /customer/cart`.
- Atualizar item — `UpdateCartImpl`, `PUT /customer/cart/update/{cartItemId}`.
- Remover item — `DeleteUniqueProductCartImpl`, `DELETE /customer/cart/item/{cartItemId}`.
- Limpar carrinho — `DeleteAllCartImpl`; não há rota correspondente no `CartController` atual.

O controller deriva o email da autenticação e recusa chamadas sem usuário autenticado. Serviços separados validam produto/estoque e coordenam atualização/cancelamento da reserva. Consulte as implementações para detalhes de compensação e falhas entre módulos.

## Referências

- [Controller](infra/controller/CartController.java)
- [Use cases](application/usecase/)
- [Integrações](application/service/)
- [Domínio](domain/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/cart.md)
