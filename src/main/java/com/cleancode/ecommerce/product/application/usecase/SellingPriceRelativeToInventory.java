package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.product.application.service.contract.ProductPriceService;
import com.cleancode.ecommerce.product.application.usecase.contract.SellingPriceToInventory;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;

public class SellingPriceRelativeToInventory implements SellingPriceToInventory {

	private final ProductRepository productRepository;
	private final ProductPriceService productPriceService;

	public SellingPriceRelativeToInventory(ProductRepository productRepository, ProductPriceService productPriceService) {
		this.productRepository = productRepository;
		this.productPriceService = productPriceService;
	}
	
	@Override
	public void execute(String productId, String stockId) {
		
		Product product = productPriceService.productPriceService(productId, stockId);
		productRepository.save(product);
	}
}