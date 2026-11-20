package com.cleancode.ecommerce.recommendation.application.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatResponse;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationDto;
import com.cleancode.ecommerce.recommendation.infra.client.RagRecommendationClient;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

@Service
public class RecommendationChatServiceImpl implements RecommendationChatService {
	private final RagRecommendationClient ragClient;
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;

	public RecommendationChatServiceImpl(RagRecommendationClient ragClient, ProductRepository productRepository,
			StockRepository stockRepository) {
		this.ragClient = ragClient;
		this.productRepository = productRepository;
		this.stockRepository = stockRepository;
	}

	@Override
	public RecommendationChatResponse recommend(String message) {
		RagRecommendationClient.RagAnswer ragAnswer = ragClient.recommend(message);
		Map<String, Integer> stockByProduct = new HashMap<>();
		for (Stock stock : stockRepository.findAll()) {
			stockByProduct.merge(stock.getProductId().getProductId(), stock.getQuantityAvailable(), Integer::sum);
		}
		List<RecommendationDto> recommendations = new ArrayList<>();
		if (ragAnswer != null && ragAnswer.recommendations() != null) {
			for (RagRecommendationClient.RagProduct candidate : ragAnswer.recommendations()) {
				if (candidate == null || candidate.id() == null) continue;
				Product product = productRepository.findById(candidate.id()).orElse(null);
				int available = stockByProduct.getOrDefault(candidate.id(), 0);
				if (product == null || !product.isActive() || available <= 0) continue;
				recommendations.add(new RecommendationDto(candidate.id(), product.getName().getName(),
						product.getDescription().getDescription(), product.getProductCategory(), product.getBrand().getBrand(),
						product.getPrice().getPrice(), product.getPrice().getCoin(), available,
						candidate.reason() == null ? "Encontrado pela busca semântica do catálogo." : candidate.reason()));
			}
		}
		return new RecommendationChatResponse(ragAnswer == null ? "Não consegui gerar recomendações agora."
				: ragAnswer.answer(), List.copyOf(recommendations));
	}
}
