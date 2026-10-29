package com.cleancode.ecommerce.order.domain.shipping;

import java.math.BigDecimal;

public class FreeShippingStrategy implements ShippingCostStrategy {
	@Override public boolean supports(BigDecimal itemsTotal) { return itemsTotal.compareTo(new BigDecimal("100.00")) >= 0; }
	@Override public BigDecimal calculate(BigDecimal itemsTotal) { return BigDecimal.ZERO; }
}
