package com.cleancode.ecommerce.product.application.usecase.contract;

import com.cleancode.ecommerce.product.application.dto.input.CreateProductDto;

public interface CreateProduct {

	void execute(CreateProductDto dto);

}
