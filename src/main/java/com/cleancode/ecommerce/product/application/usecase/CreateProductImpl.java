package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.event.product.EventPublisher;
import com.cleancode.ecommerce.event.product.ProductEvent;
import com.cleancode.ecommerce.product.application.dto.input.CreateProductDto;
import com.cleancode.ecommerce.product.application.cache.ProductCacheNames;
import com.cleancode.ecommerce.product.application.usecase.contract.CreateProduct;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;

public class CreateProductImpl implements CreateProduct{

	private final ProductRepository repository;
	private final EventPublisher eventPublisher; 
	
	public CreateProductImpl(ProductRepository repository, EventPublisher eventPublisher) {
		this.repository = repository;
		this.eventPublisher = eventPublisher;
	}
	
	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void execute(CreateProductDto dto) {
		Product product = dto.toProduct();
		
		repository.save(product);
		eventPublisher.publish(new ProductEvent(product.getProductId().getProductId()));
	}
}
