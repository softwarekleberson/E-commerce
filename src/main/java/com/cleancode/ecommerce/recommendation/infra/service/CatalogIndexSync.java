package com.cleancode.ecommerce.recommendation.infra.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.recommendation.infra.client.RagRecommendationClient;

@Component
public class CatalogIndexSync {
	private static final Logger log = LoggerFactory.getLogger(CatalogIndexSync.class);
	private final ProductRepository productRepository;
	private final RagRecommendationClient ragClient;
	private volatile String lastHash;

	public CatalogIndexSync(ProductRepository productRepository, RagRecommendationClient ragClient) {
		this.productRepository = productRepository;
		this.ragClient = ragClient;
	}

	@Scheduled(initialDelayString = "${recommendation.rag.index-initial-delay-ms:10000}",
			fixedDelayString = "${recommendation.rag.index-refresh-delay-ms:43200000}")
	public void synchronize() {
		try {
			List<RagRecommendationClient.CatalogProduct> catalog = loadCatalog();
			String hash = hash(catalog.toString());
			if (hash.equals(lastHash)) return;
			ragClient.replaceIndex(catalog);
			lastHash = hash;
			log.info("FAISS recommendation catalog synchronized: {} products", catalog.size());
		} catch (Exception exception) {
			log.warn("Could not synchronize product catalog with the RAG service: {}", exception.getMessage());
		}
	}

	private List<RagRecommendationClient.CatalogProduct> loadCatalog() {
		List<RagRecommendationClient.CatalogProduct> products = new ArrayList<>();
		int pageNumber = 0;
		Page<Product> page;
		do {
			page = productRepository.ListAllProductActive(PageRequest.of(pageNumber++, 200));
			for (Product product : page.getContent()) {
				products.add(new RagRecommendationClient.CatalogProduct(product.getProductId().getProductId(),
						product.getName().getName(), product.getDescription().getDescription(),
						product.getProductCategory().name(), product.getBrand().getBrand(),
						product.getPrice().getPrice().toPlainString(),
						product.getPrice().getCoin() == null ? "" : product.getPrice().getCoin().name()));
			}
		} while (page.hasNext());
		products.sort(java.util.Comparator.comparing(RagRecommendationClient.CatalogProduct::id));
		return List.copyOf(products);
	}

	private String hash(String value) throws Exception {
		return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
	}
}
