package com.cleancode.ecommerce.product.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.application.dto.output.ProductDtoFactory;
import com.cleancode.ecommerce.product.application.usecase.contract.SearchProducts;
import com.cleancode.ecommerce.product.domain.ProductSearchCriteria;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;

public class SearchProductsImpl implements SearchProducts {

	private final ProductRepository productRepository;

	public SearchProductsImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Page<ListProductDto> execute(ProductSearchCriteria criteria, Pageable pageable) {
		return productRepository.search(criteria, pageable).map(ProductDtoFactory::listAllProduct);
	}
}
