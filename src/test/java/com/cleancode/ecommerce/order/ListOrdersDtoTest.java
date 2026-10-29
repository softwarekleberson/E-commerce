package com.cleancode.ecommerce.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.order.application.usecase.order.dto.ListOrdersDto;
import com.cleancode.ecommerce.order.domain.Order;

class ListOrdersDtoTest {

	@Test
	void shouldExposeShippingCostInOrderListDto() {
		Order order = new Order("customer-1", "delivery-1");
		order.addItem("product", new BigDecimal("40.00"), 1, "reservation-1");

		ListOrdersDto dto = new ListOrdersDto(order);

		assertThat(dto.shippingCost()).isEqualByComparingTo("60.00");
		assertThat(dto.total()).isEqualByComparingTo("100.00");
	}
}
