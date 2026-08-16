package com.cleancode.ecommerce.dash.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;

public record OrderDto(
		
		String orderId,
		String customerId,
		String deliveryId,
		LocalDateTime createdAt,
		BigDecimal total,
		OrderStatus orderStatus,
		List<OrderItensDto> itens
		
		) {
	
	public OrderDto(Order o) {
		this(o.getOrderId().getOrderId(),
			o.getCustomerId().getValue(),
			o.getDeliveryId(),
			o.getCreatedAt(),
			o.getTotal().getTotalValue(),
			o.getOrderStatus(),
			
			o.getItems() == null ? List.of()
			: o.getItems().stream().map(OrderItensDto::new).collect(Collectors.toList())
			
			);
	}
}
