package com.cleancode.ecommerce.product.application.service;

import java.math.BigDecimal;

import com.cleancode.ecommerce.product.application.service.contract.ProductPriceService;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.shared.exception.StockNotFound;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class ProductPriceServiceImpl implements ProductPriceService {

	private final StockRepository repository;
	private final ProductRepository productRepository;
	
	public ProductPriceServiceImpl(StockRepository repository, ProductRepository productRepository) {
		this.repository = repository;
		this.productRepository = productRepository;
	}

	@Override
	public Product productPriceService(String productId, String stockId) {
		
		Stock stock = repository.findByStock(stockId)
		.orElseThrow(() -> new StockNotFound("Stock with id:" + stockId + " not found "));
		
		Product product = productRepository.findById(productId)
		.orElseThrow(() -> new ProductNotFound("Product with id : " + productId + "not found"));
					
		BigDecimal pricing = product.getPricing().getPricing();
		BigDecimal highestPurchasePrice = stock.getProductInput().stream()
				.map(input -> input.getPurchasePrice().getPrice()).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

		product.salePriceWithinMarginPolicy(pricing, highestPurchasePrice, product.getPrice().getCoin());
		return product;
	}
}