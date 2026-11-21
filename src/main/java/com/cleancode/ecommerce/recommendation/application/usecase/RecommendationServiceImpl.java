package com.cleancode.ecommerce.recommendation.application.usecase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.PageRequest;

import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.repository.OrderRepository;
import com.cleancode.ecommerce.order.domain.state.order.OrderStatus;
import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.ProductSearchCriteria;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationDto;
import com.cleancode.ecommerce.shared.kernel.domain.TypeCoin;
import com.cleancode.ecommerce.shared.kernel.exception.CustomerNotFoundException;
import com.cleancode.ecommerce.shared.kernel.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;
import com.cleancode.ecommerce.wishlist.domain.WishlistItem;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

public class RecommendationServiceImpl implements RecommendationService {

	private static final int HISTORY_PAGE_SIZE = 100;
	private static final int CANDIDATE_PAGE_SIZE = 100;
	private static final int MAX_LIMIT = 20;
	private static final int WISHLIST_SCORE = 100;
	private static final int PURCHASE_CATEGORY_SCORE = 10;

	private final CustomerIdentityIdService customerIdentityIdService;
	private final OrderRepository orderRepository;
	private final WishlistRepository wishlistRepository;
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;

	public RecommendationServiceImpl(CustomerIdentityIdService customerIdentityIdService,
			OrderRepository orderRepository, WishlistRepository wishlistRepository,
			ProductRepository productRepository, StockRepository stockRepository) {
		this.customerIdentityIdService = customerIdentityIdService;
		this.orderRepository = orderRepository;
		this.wishlistRepository = wishlistRepository;
		this.productRepository = productRepository;
		this.stockRepository = stockRepository;
	}

	@Override
	public List<RecommendationDto> recommend(String email, ProductCategory category, BigDecimal maximumPrice,
			TypeCoin typeCoin, int limit) {
		if (limit < 1 || limit > MAX_LIMIT) {
			throw new IllegalDomainException("Recommendation limit must be between 1 and " + MAX_LIMIT);
		}
		if (maximumPrice != null && maximumPrice.signum() < 0) {
			throw new IllegalDomainException("Maximum price cannot be negative");
		}

		String customerId = customerIdentityIdService.customerIdentityIdService(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		Set<String> wishlistProductIds = wishlistRepository.findByCustomerId(customerId).stream()
				.map(WishlistItem::getProductId).collect(java.util.stream.Collectors.toSet());

		Map<String, Integer> purchasedProductCounts = new HashMap<>();
		Map<ProductCategory, Integer> purchasedCategoryCounts = new HashMap<>();
		Map<String, ProductCategory> purchasedProductCategories = new HashMap<>();
		Set<String> categoryLookupAttempted = new HashSet<>();
		List<Order> orders = orderRepository.getOrdersByCustomer(customerId, PageRequest.of(0, HISTORY_PAGE_SIZE))
				.getContent();
		for (Order order : orders) {
			if (order.getOrderStatus() != OrderStatus.PAID) {
				continue;
			}
			for (OrderItem item : order.getItems()) {
				String productId = item.getProductReferenceId();
				if (productId == null || productId.isBlank()) {
					continue;
				}
				purchasedProductCounts.merge(productId, item.getQuantity(), Integer::sum);
				if (categoryLookupAttempted.add(productId)) {
					productRepository.findById(productId)
							.ifPresent(product -> purchasedProductCategories.put(productId, product.getProductCategory()));
				}
				ProductCategory purchasedCategory = purchasedProductCategories.get(productId);
				if (purchasedCategory != null) {
					purchasedCategoryCounts.merge(purchasedCategory, item.getQuantity(), Integer::sum);
				}
			}
		}

		ProductSearchCriteria criteria = new ProductSearchCriteria(null, null, null, null, maximumPrice,
				typeCoin, category, null, null, null, null, null, null);
		List<ListProductDto> candidates = productRepository.search(criteria, PageRequest.of(0, CANDIDATE_PAGE_SIZE))
				.getContent().stream().map(com.cleancode.ecommerce.product.application.dto.output.ProductDtoFactory::listAllProduct)
				.toList();
		Map<String, Integer> availableByProductId = new HashMap<>();
		for (Stock stock : stockRepository.findAll()) {
			availableByProductId.merge(stock.getProductId().getProductId(), stock.getQuantityAvailable(), Integer::sum);
		}

		List<ScoredRecommendation> recommendations = new ArrayList<>();
		for (ListProductDto product : candidates) {
			int available = availableByProductId.getOrDefault(product.getId(), 0);
			if (available <= 0) {
				continue;
			}

			int score = 0;
			List<String> reasons = new ArrayList<>();
			if (wishlistProductIds.contains(product.getId())) {
				score += WISHLIST_SCORE;
				reasons.add("Está na sua lista de desejos.");
			}
			int categoryPurchases = purchasedCategoryCounts.getOrDefault(product.getCategory(), 0);
			if (categoryPurchases > 0) {
				score += Math.min(categoryPurchases, 10) * PURCHASE_CATEGORY_SCORE;
				reasons.add("Relacionado a categorias das suas compras anteriores.");
			}
			if (purchasedProductCounts.containsKey(product.getId())) {
				score += Math.min(purchasedProductCounts.get(product.getId()), 5) * 2;
				reasons.add("Você já comprou este produto.");
			}
			if (reasons.isEmpty()) {
				reasons.add("Produto ativo e disponível em estoque.");
			}

			recommendations.add(new ScoredRecommendation(score, new RecommendationDto(product.getId(), product.getName(),
					product.getDescription(), product.getCategory(), product.getBrand(), product.getPrice(),
					product.getTypeCoin(), available, String.join(" ", reasons))));
		}

		return recommendations.stream()
				.sorted(Comparator.comparingInt(ScoredRecommendation::score).reversed()
						.thenComparing(r -> r.recommendation().name(), String.CASE_INSENSITIVE_ORDER))
				.limit(limit)
				.map(ScoredRecommendation::recommendation)
				.toList();
	}

	private record ScoredRecommendation(int score, RecommendationDto recommendation) {
	}
}
