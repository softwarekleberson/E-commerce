package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.product.application.dto.input.ProductStatusChangeDto;
import com.cleancode.ecommerce.product.application.usecase.contract.ManualProductActivation;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;

public class ManualProductActivationImpl implements ManualProductActivation {

	private final ProductRepository productRepository;

	public ManualProductActivationImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void execute(ProductStatusChangeDto dto) {
		Product product = productRepository.findById(dto.productId())
				.orElseThrow(() -> new ProductNotFound("Product with id : " + dto.productId() + "not found"));
		
		product.productStatusPolicyActivation(dto.justification(), dto.category());

		productRepository.save(product);
	}
}