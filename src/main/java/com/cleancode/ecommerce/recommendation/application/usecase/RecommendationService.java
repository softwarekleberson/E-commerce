package com.cleancode.ecommerce.recommendation.application.usecase;

import java.math.BigDecimal;
import java.util.List;

import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.shared.kernel.domain.TypeCoin;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationDto;

public interface RecommendationService {

	List<RecommendationDto> recommend(String email, ProductCategory category, BigDecimal maximumPrice,
			TypeCoin typeCoin, int limit);
}
