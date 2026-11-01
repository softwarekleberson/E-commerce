package com.cleancode.ecommerce.order.domain.shipping;

import java.math.BigDecimal;

public class ExpensiveShippingStrategy implements ShippingCostStrategy {
	@Override public boolean supports(BigDecimal itemsTotal) { return itemsTotal.compareTo(new BigDecimal("50.00")) < 0; }
	@Override public BigDecimal calculate(BigDecimal itemsTotal) { return new BigDecimal("15.00"); }
}
