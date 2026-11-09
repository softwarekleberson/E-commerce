package com.cleancode.ecommerce.order.domain.shipping;

import java.math.BigDecimal;

public interface ShippingCostStrategy {
	boolean supports(BigDecimal itemsTotal);
	BigDecimal calculate(BigDecimal itemsTotal);
}
