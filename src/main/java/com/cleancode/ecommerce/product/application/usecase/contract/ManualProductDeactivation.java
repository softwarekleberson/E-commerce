package com.cleancode.ecommerce.product.application.usecase.contract;

import com.cleancode.ecommerce.product.application.dto.input.ProductStatusChangeDto;

public interface ManualProductDeactivation {

	void execute(ProductStatusChangeDto dto);
}
