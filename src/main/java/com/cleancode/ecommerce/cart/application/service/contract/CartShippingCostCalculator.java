package com.cleancode.ecommerce.cart.application.service.contract;

import java.math.BigDecimal;

public interface CartShippingCostCalculator {

	BigDecimal calculate(BigDecimal itemsTotal);
}
