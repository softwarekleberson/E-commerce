package com.cleancode.ecommerce.cart.application.dtos.output;

import java.math.BigDecimal;

import com.cleancode.ecommerce.cart.domain.CartItem;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public record ListCartItensDto(

		String reservationId,
		String cartItemId,
		String productId,
		String productName,
		String urlProduct,
		int quantity,
		BigDecimal unitPrice,
		BigDecimal subtotal,
		TypeCoin coin
		
							) {

	public ListCartItensDto(CartItem itens) {
		this(itens.getReservationId(),
			 itens.getCartItemId().getCartItemId(),
			 itens.getProductId().getProductId(),
			 itens.getProductName().getName(),
			 itens.getUrlProduct().getUrlProduct(),
			 itens.getQuantity().getQuantity(),
			 itens.getUnitPrice().getPrice(),
			 itens.getSubtotal().getPrice(),
			 itens.getSubtotal().getCoin());
	}
}