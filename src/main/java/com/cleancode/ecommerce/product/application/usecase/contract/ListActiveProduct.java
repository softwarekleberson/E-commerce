package com.cleancode.ecommerce.product.application.usecase.contract;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;

public interface ListActiveProduct {

	ListProductDto execute(String idProduct);
}
