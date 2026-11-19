package com.cleancode.ecommerce.recommendation.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.recommendation.application.usecase.RecommendationService;
import com.cleancode.ecommerce.recommendation.application.usecase.RecommendationServiceImpl;
import com.cleancode.ecommerce.shared.kernel.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

@Configuration
public class RecommendationConfig {

	@Bean
	public RecommendationService recommendationService(CustomerIdentityIdService customerIdentityIdService,
			OrderRepository orderRepository, WishlistRepository wishlistRepository,
			ProductRepository productRepository, StockRepository stockRepository) {
		return new RecommendationServiceImpl(customerIdentityIdService, orderRepository, wishlistRepository,
				productRepository, stockRepository);
	}
}
