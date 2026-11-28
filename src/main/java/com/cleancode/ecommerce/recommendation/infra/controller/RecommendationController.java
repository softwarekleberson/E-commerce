package com.cleancode.ecommerce.recommendation.infra.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationDto;
import com.cleancode.ecommerce.recommendation.application.usecase.RecommendationService;
import com.cleancode.ecommerce.shared.kernel.domain.TypeCoin;

@RestController
@RequestMapping("/customer/recommendations")
public class RecommendationController {

	private final RecommendationService recommendationService;

	public RecommendationController(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@GetMapping
	public ResponseEntity<List<RecommendationDto>> recommend(Authentication authentication,
			@RequestParam(required = false) ProductCategory category,
			@RequestParam(required = false) BigDecimal maximumPrice,
			@RequestParam(defaultValue = "REAL") TypeCoin typeCoin,
			@RequestParam(defaultValue = "10") int limit) {
		return ResponseEntity.ok(recommendationService.recommend(authentication.getName(), category, maximumPrice,
				typeCoin, limit));
	}
}
