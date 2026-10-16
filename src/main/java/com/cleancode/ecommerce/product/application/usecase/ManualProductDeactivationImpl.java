package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.product.application.dto.input.ProductStatusChangeDto;
import com.cleancode.ecommerce.product.application.cache.ProductCacheNames;
import com.cleancode.ecommerce.product.application.usecase.contract.ManualProductDeactivation;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;

public class ManualProductDeactivationImpl implements ManualProductDeactivation{

	private final ProductRepository productRepository;
	
	public ManualProductDeactivationImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void execute(ProductStatusChangeDto dto) {
		Product product = productRepository.findById(dto.productId())
	    .orElseThrow(() -> new ProductNotFound("Product with id : " + dto.productId() + "not found"));
		
		product.productStatusPolicyManualDeactivation(dto.justification(), dto.category());
		
		productRepository.save(product);
	}
}
