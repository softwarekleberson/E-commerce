package com.cleancode.ecommerce.dash.dto;

import java.math.BigDecimal;

import com.cleancode.ecommerce.order.domain.OrderItem;

public record OrderItensDto(
		
		String productId,
		BigDecimal price,
		int quantity,
		BigDecimal subtotal
		
		) {

	public OrderItensDto (OrderItem o) {
		this(o.getProductId(), o.getPrice(), o.getQuantity(), o.getSubtotal());
	}
}