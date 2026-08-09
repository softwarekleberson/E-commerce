package com.cleancode.ecommerce.order.application.usecase.dto;

import java.math.BigDecimal;
import com.cleancode.ecommerce.order.domain.OrderItem;

public record ListItensDto(
		
		String productId, BigDecimal price, int quantity, BigDecimal subtotal, String reservationId 
		
		) {

	public ListItensDto (OrderItem i) {
		this(i.getProductId(), i.getPrice(), i.getQuantity(), i.getSubtotal(), i.getStockOutId());
	}
}
