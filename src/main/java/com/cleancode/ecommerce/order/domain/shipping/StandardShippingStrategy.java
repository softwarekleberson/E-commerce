package com.cleancode.ecommerce.order.domain.shipping;

import java.math.BigDecimal;

public class StandardShippingStrategy implements ShippingCostStrategy {
	@Override public boolean supports(BigDecimal itemsTotal) { return itemsTotal.compareTo(new BigDecimal("50.00")) >= 0 && itemsTotal.compareTo(new BigDecimal("100.00")) < 0; }
	@Override public BigDecimal calculate(BigDecimal itemsTotal) { return new BigDecimal("10.00"); }
}
