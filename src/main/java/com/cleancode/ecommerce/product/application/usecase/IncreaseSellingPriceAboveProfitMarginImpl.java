package com.cleancode.ecommerce.product.application.usecase;

import com.cleancode.ecommerce.product.application.dto.input.ModifySellingPriceDto;
import com.cleancode.ecommerce.product.application.usecase.contract.IncreaseSellingPriceAboveProfitMargin;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.exception.ProductNotFound;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;

public class IncreaseSellingPriceAboveProfitMarginImpl implements IncreaseSellingPriceAboveProfitMargin {

	private final ProductRepository repository;

	public IncreaseSellingPriceAboveProfitMarginImpl(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute(ModifySellingPriceDto dto) {
		Product product = repository.findById(dto.productId())
				.orElseThrow(() -> new ProductNotFound("Product with id : " + dto.productId() + "not found"));
		
		product.aboveMarginSalesPricePolicy(dto.newPrice());
		repository.save(product);
	}
}