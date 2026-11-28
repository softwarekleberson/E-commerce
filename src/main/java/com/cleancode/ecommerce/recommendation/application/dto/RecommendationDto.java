package com.cleancode.ecommerce.recommendation.application.dto;

import java.math.BigDecimal;

import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.shared.kernel.domain.TypeCoin;

public record RecommendationDto(
		String productId,
		String name,
		String description,
		ProductCategory category,
		String brand,
		BigDecimal price,
		TypeCoin typeCoin,
		int availableQuantity,
		String reason) {
}
