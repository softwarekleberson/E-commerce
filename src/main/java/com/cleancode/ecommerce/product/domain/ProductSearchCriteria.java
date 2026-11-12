package com.cleancode.ecommerce.product.domain;

import java.math.BigDecimal;

import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public record ProductSearchCriteria(
		String id,
		String name,
		String description,
		BigDecimal minimumPrice,
		BigDecimal maximumPrice,
		TypeCoin typeCoin,
		ProductCategory category,
		String brand,
		String mediaId,
		String mediaUrl,
		String mediaDescription,
		BigDecimal minimumPricing,
		BigDecimal maximumPricing) {
}
