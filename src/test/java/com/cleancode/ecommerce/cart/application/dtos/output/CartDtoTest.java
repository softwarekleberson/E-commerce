package com.cleancode.ecommerce.cart.application.dtos.output;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.cart.domain.Cart;
import com.cleancode.ecommerce.order.domain.shipping.ExpensiveShippingStrategy;
import com.cleancode.ecommerce.order.domain.shipping.FreeShippingStrategy;
import com.cleancode.ecommerce.order.domain.shipping.ShippingCostStrategyFactory;
import com.cleancode.ecommerce.order.domain.shipping.StandardShippingStrategy;
import com.cleancode.ecommerce.shared.kernel.domain.CartId;
import com.cleancode.ecommerce.shared.kernel.domain.CustomerId;

class CartDtoTest {

	@Test
	void shouldExposeShippingCostAlongsideCartSubtotal() {
		Cart cart = new Cart(new CartId("cart-123"), new CustomerId("customer-123"));
		ShippingCostStrategyFactory shippingStrategyFactory = new ShippingCostStrategyFactory(List.of(
				new FreeShippingStrategy(), new StandardShippingStrategy(), new ExpensiveShippingStrategy()));

		CartDto dto = new CartDto(cart, shippingStrategyFactory.calculate(cart.getTotalPrice().getPrice()));

		assertThat(dto.totalPrice()).isEqualByComparingTo("0");
		assertThat(dto.shippingCost()).isEqualByComparingTo("15.00");
	}
}
