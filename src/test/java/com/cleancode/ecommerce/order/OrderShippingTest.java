package com.cleancode.ecommerce.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.order.domain.Order;

class OrderShippingTest {

	@Test
	void shouldIncludeShippingCostInOrderTotal() {
		Order order = new Order("customer-1", "delivery-1");
		order.addItem("product", new BigDecimal("40.00"), 1, "reservation-1");

		assertThat(order.getShippingCost()).isEqualByComparingTo("60.00");
		assertThat(order.calculateTotal().getTotalValue()).isEqualByComparingTo("100.00");
	}
}
