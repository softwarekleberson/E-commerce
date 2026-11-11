package com.cleancode.ecommerce.adm.application.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.state.order.OrderStatus;

public record AdmOrderDto(
		String orderId,
		String customerId,
		List<AdmOrderItemDto> items,
		BigDecimal total,
		BigDecimal shippingCost,
		String currency,
		OrderStatus orderStatus,
		LocalDateTime data
) {
	public AdmOrderDto(Order order) {
		this(
				order.getOrderId().getOrderId(),
				order.getCustomerId().getValue(),
				order.getItems().stream().map(AdmOrderItemDto::new).toList(),
				order.getTotal().getTotalValue(),
				order.getShippingCost(),
				order.getTotal().getTypeCoin().toString(),
				order.getOrderStatus(),
				order.getCreatedAt()
		);
	}
}
