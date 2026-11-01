package com.cleancode.ecommerce.order.shipping;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.order.domain.shipping.ExpensiveShippingStrategy;
import com.cleancode.ecommerce.order.domain.shipping.FreeShippingStrategy;
import com.cleancode.ecommerce.order.domain.shipping.ShippingCostStrategyFactory;
import com.cleancode.ecommerce.order.domain.shipping.StandardShippingStrategy;

class ShippingCostStrategyFactoryTest {

	private ShippingCostStrategyFactory factory;

	@BeforeEach
	void setUp() {
		factory = new ShippingCostStrategyFactory(List.of(
				new FreeShippingStrategy(), new StandardShippingStrategy(), new ExpensiveShippingStrategy()));
	}

	@Test
	void shouldChargeFifteenWhenSubtotalIsBelowFifty() {
		assertThat(factory.calculate(new BigDecimal("49.99"))).isEqualByComparingTo("15.00");
	}

	@Test
	void shouldChargeTenWhenSubtotalIsFifty() {
		assertThat(factory.calculate(new BigDecimal("50.00"))).isEqualByComparingTo("10.00");
	}

	@Test
	void shouldChargeTenWhenSubtotalIsBelowOneHundred() {
		assertThat(factory.calculate(new BigDecimal("99.99"))).isEqualByComparingTo("10.00");
	}

	@Test
	void shouldOfferFreeShippingWhenSubtotalIsOneHundred() {
		assertThat(factory.calculate(new BigDecimal("100.00"))).isEqualByComparingTo("0.00");
	}

	@Test
	void shouldOfferFreeShippingWhenSubtotalIsAboveOneHundred() {
		assertThat(factory.calculate(new BigDecimal("150.00"))).isEqualByComparingTo("0.00");
	}
}
