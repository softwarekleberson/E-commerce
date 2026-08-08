package com.cleancode.ecommerce.product.application.dto.input;

import java.math.BigDecimal;

public record ModifySellingPriceDto(String productId, BigDecimal newPrice) {

}
