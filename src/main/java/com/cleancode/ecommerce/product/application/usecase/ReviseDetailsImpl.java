package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.product.application.dto.input.ReviseDetailsDto;
import com.cleancode.ecommerce.product.application.cache.ProductCacheNames;
import com.cleancode.ecommerce.product.application.usecase.contract.ReviseDetails;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;

public class ReviseDetailsImpl implements ReviseDetails{

	private final ProductRepository repository;

	public ReviseDetailsImpl(ProductRepository repository) {
		this.repository = repository;
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void execute(String productId, ReviseDetailsDto dto) {
		Product product = repository.findById(productId)
				.orElseThrow(() -> new ProductNotFound("Product with id : " + productId + "not found"));
		
		product.reviseDetails(dto.newName(), dto.newDescription());
		repository.save(product);
	}
}
