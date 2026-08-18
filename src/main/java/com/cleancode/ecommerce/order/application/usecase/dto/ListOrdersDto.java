package com.cleancode.ecommerce.order.application.usecase.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderStatus;

public record ListOrdersDto(
		
		String orderId, String customerId, List<ListItensDto> itens,
		BigDecimal total, String currency, OrderStatus orderStatus,
		LocalDateTime data
		
		) {

	public ListOrdersDto (Order o) {
		this(
			o.getOrderId().getOrderId(),
			o.getCustomerId().getValue(),
			o.getItems() == null ? List.of() :
			o.getItems().stream().map(ListItensDto::new).collect(Collectors.toList()),
			o.getTotal().getTotalValue(),
			o.getTotal().getTypeCoin().toString(),
			o.getOrderStatus(),
			o.getCreatedAt()
		);
	}
}
