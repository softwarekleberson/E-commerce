package com.cleancode.ecommerce.cart.infra.mapper;

import com.cleancode.ecommerce.cart.domain.CartItemId;
import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.cart.infra.persistence.CartEntity;
import com.cleancode.ecommerce.cart.infra.persistence.CartItemEntity;
import com.cleancode.ecommerce.cart.infra.persistence.TypeCoinEntity;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.UrlProduct;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class CartItemMapper {

	public static CartItemEntity toEntity(CartItem item, CartEntity cartEntity) {
		CartItemEntity entity = new CartItemEntity();
		entity.setCart_item_id(item.getCartItemId().getCartItemId());
		entity.setCart(cartEntity);
		entity.setProduct_id(item.getProductId().getProductId());
		entity.setProduct_name(item.getProductName().getName());
		entity.setUrl_product(item.getUrlProduct().getUrlProduct());
		entity.setQuantity(item.getQuantity().getQuantity());
		entity.setUnit_price(item.getUnitPrice().getPrice());
		entity.setCoin(TypeCoinEntity.valueOf(item.getUnitPrice().getCoin().name()));
		entity.setSubtotal(item.getSubtotal().getPrice());

		// CORREÇÃO: Extrair a String de dentro do Value Object usando o método de leitura dele
		entity.setReservation_id(item.getReservationId());
		return entity;
	}

	public static CartItem toDomain(CartItemEntity entity) {
		return new CartItem(new CartItemId(entity.getCart_item_id()), new ProductId(entity.getProduct_id()),
				new Name(entity.getProduct_name()), new UrlProduct(entity.getUrl_product()) ,new Quantity(entity.getQuantity()),
				new Price(entity.getUnit_price(), TypeCoin.valueOf(entity.getCoin().name())),
				new ReservationId(entity.getReservation_id()));
	}
}