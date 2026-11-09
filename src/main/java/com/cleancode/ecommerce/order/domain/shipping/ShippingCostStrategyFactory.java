package com.cleancode.ecommerce.order.domain.shipping;

import java.math.BigDecimal;
import java.util.List;

public class ShippingCostStrategyFactory {
	private final List<ShippingCostStrategy> strategies;

	public ShippingCostStrategyFactory(List<ShippingCostStrategy> strategies) { this.strategies = List.copyOf(strategies); }

	public BigDecimal calculate(BigDecimal itemsTotal) {
		return strategies.stream().filter(strategy -> strategy.supports(itemsTotal)).findFirst()
				.orElseThrow(() -> new IllegalStateException("No shipping strategy for order total"))
				.calculate(itemsTotal);
	}
}
