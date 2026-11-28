package com.cleancode.ecommerce.recommendation.application.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.order.domain.state.order.OrderStatus;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatFeedbackResponse;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatResponse;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationDto;
import com.cleancode.ecommerce.recommendation.infra.client.RagRecommendationClient;
import com.cleancode.ecommerce.recommendation.infra.gateway.RecommendationChatFeedbackJpa;
import com.cleancode.ecommerce.recommendation.infra.persistence.RecommendationChatFeedbackEntity;
import com.cleancode.ecommerce.shared.kernel.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

@Service
public class RecommendationChatServiceImpl implements RecommendationChatService {
	private static final int PURCHASE_HISTORY_PAGE_SIZE = 100;
	private static final int MAX_PURCHASED_PRODUCTS_TO_CLASSIFY = 20;
	private static final int MAX_PURCHASE_CATEGORIES = 5;

	private final RagRecommendationClient ragClient;
	private final CustomerIdentityIdService customerIdentityIdService;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;
	private final RecommendationChatFeedbackJpa feedbackRepository;

	public RecommendationChatServiceImpl(RagRecommendationClient ragClient,
			CustomerIdentityIdService customerIdentityIdService, OrderRepository orderRepository,
			ProductRepository productRepository, StockRepository stockRepository,
			RecommendationChatFeedbackJpa feedbackRepository) {
		this.ragClient = ragClient;
		this.customerIdentityIdService = customerIdentityIdService;
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.stockRepository = stockRepository;
		this.feedbackRepository = feedbackRepository;
	}

	@Override
	public RecommendationChatResponse recommend(String message, String userEmail) {
		List<RagRecommendationClient.PurchasePreference> purchasePreferences = purchasePreferences(userEmail);
		RagRecommendationClient.RagAnswer ragAnswer = ragClient.recommend(message, purchasePreferences);
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
		String responseId = UUID.randomUUID().toString();
		feedbackRepository.save(new RecommendationChatFeedbackEntity(responseId, userEmail, null, null, null));
		return new RecommendationChatResponse(responseId,
				ragAnswer == null ? "Não consegui gerar recomendações agora." : ragAnswer.answer(),
				List.copyOf(recommendations), null);
	}

	private List<RagRecommendationClient.PurchasePreference> purchasePreferences(String userEmail) {
		return customerIdentityIdService.customerIdentityIdService(userEmail)
				.map(this::purchasePreferencesForCustomer)
				.orElseGet(List::of);
	}

	private List<RagRecommendationClient.PurchasePreference> purchasePreferencesForCustomer(String customerId) {
		Map<String, Integer> purchasedProductCounts = new HashMap<>();
		List<Order> orders = orderRepository
				.getOrdersByCustomer(customerId, PageRequest.of(0, PURCHASE_HISTORY_PAGE_SIZE)).getContent();
		for (Order order : orders) {
			if (order.getOrderStatus() != OrderStatus.PAID) continue;
			for (OrderItem item : order.getItems()) {
				String productId = item.getProductReferenceId();
				if (productId != null && !productId.isBlank()) {
					purchasedProductCounts.merge(productId, item.getQuantity(), Integer::sum);
				}
			}
		}

		Map<ProductCategory, Integer> categoryCounts = new HashMap<>();
		purchasedProductCounts.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
						.thenComparing(Map.Entry.comparingByKey()))
				.limit(MAX_PURCHASED_PRODUCTS_TO_CLASSIFY)
				.forEach(entry -> productRepository.findById(entry.getKey()).ifPresent(product -> {
					ProductCategory category = product.getProductCategory();
					if (category != null) categoryCounts.merge(category, entry.getValue(), Integer::sum);
				}));

		return categoryCounts.entrySet().stream()
				.sorted(Map.Entry.<ProductCategory, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(entry -> entry.getKey().name()))
				.limit(MAX_PURCHASE_CATEGORIES)
				.map(entry -> new RagRecommendationClient.PurchasePreference(entry.getKey().name(), entry.getValue()))
				.toList();
	}

	@Override
	public RecommendationChatFeedbackResponse submitFeedback(String responseId, String userEmail, boolean liked) {
		RecommendationChatFeedbackEntity interaction = feedbackRepository
				.findByResponseIdAndUserEmail(responseId, userEmail)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Recommendation response not found"));
		interaction.setLiked(liked);
		interaction.setFeedbackAt(LocalDateTime.now());
		feedbackRepository.save(interaction);
		return new RecommendationChatFeedbackResponse(responseId, liked);
	}
}
