# Módulo `wishlist`

## Responsabilidade

Mantém a lista de produtos desejados do cliente. Produtos entram na lista como reação à remoção do carrinho ou à expiração de uma reserva; a lista não reserva estoque.

## Estrutura e arquitetura

- `application/dto/`: resposta com identificador do produto e data de inclusão.
- `application/usecase/`: contrato e implementação para listar e remover itens; resolve o identificador do cliente a partir do email.
- `domain/`: `WishlistItem` e contrato `WishlistRepository`.
- `infra/config/`: composição do serviço de aplicação.
- `infra/controller/`: API HTTP de consulta e remoção.
- `infra/event/`: listeners para eventos de adição e remoção de produto no carrinho.
- `infra/gateway/` e `infra/persistence/`: adapter JPA, repositório Spring Data e entidade persistida.

## Operações e integrações

- `GET /customer/wishlist` lista os itens do cliente autenticado.
- `DELETE /customer/wishlist/{productId}` remove um produto da lista.
- `ProductRemovedFromCartEventListener` salva o produto quando o carrinho publica o evento de remoção.
- `ProductAddedToCartEventListener` remove da lista o produto adicionado novamente ao carrinho.

Os eventos são publicados pelo módulo `event` por meio do mecanismo Spring local. A rotina de expiração do carrinho também grava itens na lista através de `WishlistRepository`.

## Referências

- [Controller](infra/controller/WishlistController.java)
- [Serviço de aplicação](application/usecase/)
- [Listeners](infra/event/)
- [Domínio e repositório](domain/)
- [Persistência](infra/)
- [Visão geral da arquitetura](../../../../../../../doc/docs/architecture/wishlist.md)
- [Módulo cart](../cart/README.md)
