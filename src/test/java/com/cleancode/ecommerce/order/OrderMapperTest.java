package com.cleancode.ecommerce.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.infra.mapper.OrderMapper;

class OrderMapperTest {

	@Test
	void shouldPersistAndRestoreShippingCost() {
		Order order = new Order("customer-1", "delivery-1");
		order.addItem("book", new BigDecimal("40.00"), 1, "reservation-1");

		var entity = OrderMapper.toEntity(order);
		assertThat(entity.getShippingCost()).isEqualByComparingTo("60.00");

		entity.setShippingCost(new BigDecimal("12.34"));
		Order restoredOrder = OrderMapper.toDomain(entity);

		assertThat(restoredOrder.getShippingCost()).isEqualByComparingTo("12.34");
		assertThat(restoredOrder.getTotal().getTotalValue()).isEqualByComparingTo("52.34");
	}
}
