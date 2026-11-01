package com.cleancode.ecommerce.cart.application.dtos.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public record CartDto(
		
		String cartId,
		String customerId,
		List<ListCartItensDto> cartItens,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		BigDecimal totalPrice,
		BigDecimal shippingCost,
		TypeCoin coin
		
		) {

	public CartDto (Cart cart, BigDecimal shippingCost) {
		this(cart.getCartId().getCartId(),
			 cart.getCustomerId().getValue(),
			
			 cart.getCartItens() == null ? List.of() :
			 cart.getCartItens().stream().map(ListCartItensDto::new)
			 .collect(Collectors.toList()),
			 
			 cart.getCreatedAt(),
			 cart.getUpdatedAt(),
			 cart.getTotalPrice().getPrice(),
			 shippingCost,
			 cart.getTotalPrice().getCoin());
	}
}
