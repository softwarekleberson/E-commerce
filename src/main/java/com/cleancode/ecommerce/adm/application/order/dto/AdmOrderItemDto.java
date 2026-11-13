package com.cleancode.ecommerce.adm.application.order.dto;

import java.math.BigDecimal;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.state.itens.ItemStatus;

public record AdmOrderItemDto(
		String productId,
		BigDecimal price,
		int quantity,
		BigDecimal subtotal,
		String reservationId,
		ItemStatus itemStatus
) {
	public AdmOrderItemDto(OrderItem item) {
		this(item.getProductId(), item.getPrice(), item.getQuantity(), item.getSubtotal(),
				item.getStockOutId(), item.getItemStatus());
	}
}
