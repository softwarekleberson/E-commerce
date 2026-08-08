package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.application.dto.output.ProductDtoFactory;
import com.cleancode.ecommerce.product.application.usecase.contract.ListActiveProduct;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;

public class ListProductActivetByIdImpl implements ListActiveProduct {

	private final ProductRepository repository;

	public ListProductActivetByIdImpl(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public ListProductDto execute(String idProduct) {
		Product product = repository.findById(idProduct)
	   .orElseThrow(() -> new ProductNotFound("Product with id : " + idProduct + "not found"));
		
		if (!product.isActive()) {
			throw new ProductNotFound("Product with id: " + idProduct + " is inactive or not found");
		}
		
		return ProductDtoFactory.listAllProduct(product);
	}
}