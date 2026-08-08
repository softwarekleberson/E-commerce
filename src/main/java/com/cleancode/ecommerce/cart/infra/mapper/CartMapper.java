package com.cleancode.ecommerce.cart.infra.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.cart.domain.CartId;
import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.cart.infra.persistence.CartEntity;
import com.cleancode.ecommerce.cart.infra.persistence.CartItemEntity;
import com.cleancode.ecommerce.cart.infra.persistence.TypeCoinEntity;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;

public class CartMapper {

	public static CartEntity toEntity(Cart cart) {
		CartEntity entity = new CartEntity();
		entity.setCart_id(cart.getCartId().getCartId());
		entity.setCustomer_id(cart.getCustomerId().getValue());
		entity.setCreated_at(cart.getCreatedAt());
		entity.setUpdated_at(cart.getUpdatedAt());
		entity.setTotal_price(cart.getTotalPrice().getPrice());
		entity.setCoin(TypeCoinEntity.valueOf(cart.getTotalPrice().getCoin().name()));

		List<CartItemEntity> items = cart.getCartItens().stream().map(i -> CartItemMapper.toEntity(i, entity))
				.collect(Collectors.toList());

		entity.setCart_itens(items);
		return entity;
	}

	public static CartEntity toEntity(Cart cart, CartEntity entity) {
		entity.setUpdated_at(cart.getUpdatedAt());
		entity.setTotal_price(cart.getTotalPrice().getPrice());
		entity.setCoin(TypeCoinEntity.valueOf(cart.getTotalPrice().getCoin().name()));

		// CORREÇÃO: Em vez de .clear() e .addAll(), atualizamos os valores dos itens
		// que já existem
		cart.getCartItens().forEach(domainItem -> {
			// Procura se o item do domínio já existe na entidade vinda do banco de dados
			CartItemEntity itemEntity = entity.getCart_itens().stream()
					.filter(i -> i.getCart_item_id().equals(domainItem.getCartItemId().getCartItemId())).findFirst()
					.orElse(null);

			if (itemEntity != null) {
				itemEntity.setQuantity(domainItem.getQuantity().getQuantity());
				itemEntity.setSubtotal(domainItem.getSubtotal().getPrice());

				itemEntity.setReservation_id(domainItem.getReservationId());
			} else {
				entity.getCart_itens().add(CartItemMapper.toEntity(domainItem, entity));
			}
		});

		entity.getCart_itens().removeIf(itemEntity -> cart.getCartItens().stream().noneMatch(
				domainItem -> domainItem.getCartItemId().getCartItemId().equals(itemEntity.getCart_item_id())));

		return entity;
	}

	public static Cart toDomain(CartEntity entity) {
		List<CartItem> items = entity.getCart_itens().stream().map(CartItemMapper::toDomain)
				.collect(Collectors.toList());

		return new Cart(new CartId(entity.getCart_id()), new CustomerId(entity.getCustomer_id()), items,
				entity.getCreated_at(), entity.getUpdated_at());
	}
}