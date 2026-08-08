package com.cleancode.ecommerce.product.application.usecase.contract;

import com.cleancode.ecommerce.product.application.dto.input.ModifySellingPriceDto;

public interface IncreaseSellingPriceAboveProfitMargin {

	public void execute(ModifySellingPriceDto dto);
}
