package com.cleancode.ecommerce.product.application.service;

import com.cleancode.ecommerce.product.application.service.contract.ProductActivationService;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.shared.exception.StockNotFound;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class ProductActivationServiceImpl implements ProductActivationService {

	private final int INVENTORY_CONTROL = 0;
	
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;
	
	public ProductActivationServiceImpl(ProductRepository productRepository, StockRepository stockRepository) {
		this.productRepository = productRepository;
		this.stockRepository = stockRepository;
	}
	
	public Product activateProductIfStockAvailable(String productId, String stockId) {

		Product product = productRepository.findById(productId)
		.orElseThrow(() -> new ProductNotFound("Product with id : " + productId + " not found"));
		
		Stock stock = stockRepository.findByStock(stockId)
		.orElseThrow(() -> new StockNotFound("Stock with id : " + stockId + "not found"));
		
		if (stock.getTotalQuantity() > INVENTORY_CONTROL) {
			product.activate();
		} else if (stock.getTotalQuantity() <= INVENTORY_CONTROL) {
			product.productStatusPolicyAutomaticDeactivation();
		}
		return product;
	}
}