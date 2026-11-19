package com.cleancode.ecommerce.cart.infra.gateway;

import java.math.BigDecimal;

import com.cleancode.ecommerce.cart.application.service.contract.CartShippingCostCalculator;
import com.cleancode.ecommerce.order.domain.shipping.ShippingCostStrategyFactory;

public class CartShippingCostCalculatorAdapter implements CartShippingCostCalculator {

	private final ShippingCostStrategyFactory shippingStrategyFactory;

	public CartShippingCostCalculatorAdapter(ShippingCostStrategyFactory shippingStrategyFactory) {
		this.shippingStrategyFactory = shippingStrategyFactory;
	}

	@Override
	public BigDecimal calculate(BigDecimal itemsTotal) {
		return shippingStrategyFactory.calculate(itemsTotal);
	}
}
