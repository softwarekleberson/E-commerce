package com.cleancode.ecommerce.product.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.application.dto.output.ProductDtoFactory;
import com.cleancode.ecommerce.product.application.usecase.contract.ListAllProductActive;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;

public class ListAllProductActiveImpl implements ListAllProductActive {

	private final ProductRepository repository;

	public ListAllProductActiveImpl(ProductRepository repository) {
		this.repository = repository;
	}

	public Page<ListProductDto> execute(Pageable pageable) {
		Page<Product> products = this.repository.ListAllProductActive(pageable);
		return products.map(ProductDtoFactory::listAllProduct);
	}
}