# Módulo `wishlist`

## Responsabilidade

Mantém os produtos que o cliente removeu do carrinho ou cujo prazo de reserva expirou. Uma entrada na lista não reserva estoque.

## Operações

- `GET /customer/wishlist`: lista os identificadores de produto e a data de inclusão.
- `DELETE /customer/wishlist/{productId}`: remove um produto da lista.
- Adicionar novamente pelo `POST /customer/cart`: reserva estoque novamente e remove a entrada da lista.

O fluxo de remoção do carrinho e de expiração é coordenado pelo módulo `cart`. A persistência usa `tb_wishlist_item`, com unicidade por cliente/produto e exclusão em cascata quando cliente ou produto são removidos.

## Referências

- [Controller](../../../src/main/java/com/cleancode/ecommerce/wishlist/infra/controller/WishlistController.java)
- [Casos de uso](../../../src/main/java/com/cleancode/ecommerce/wishlist/application/usecase/)
- [Domínio e repositório](../../../src/main/java/com/cleancode/ecommerce/wishlist/domain/)
