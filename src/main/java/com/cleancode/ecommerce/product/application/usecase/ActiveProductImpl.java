package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.product.application.service.contract.ProductActivationService;
import com.cleancode.ecommerce.product.application.usecase.contract.ActiveProduct;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;

public class ActiveProductImpl implements ActiveProduct {

	private final ProductRepository productRepository;
	private final ProductActivationService productActivationService;
	
	public ActiveProductImpl(ProductRepository productRepository, ProductActivationService productActivationService) {
		this.productRepository = productRepository;
		this.productActivationService = productActivationService;
	}
	
	@Override
	public void execute(String productId, String stockId) {
		Product product = productRepository.findById(productId)
		.orElseThrow(() -> new ProductNotFound("Product with id : " + productId + " not found xxx"));
		
		product = productActivationService.activateProductIfStockAvailable(productId, stockId);
		productRepository.save(product);
	}
}