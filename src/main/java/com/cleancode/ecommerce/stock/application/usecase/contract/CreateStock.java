package com.cleancode.ecommerce.stock.application.usecase.contract;

import com.cleancode.ecommerce.stock.application.dto.ListStockDto;

public interface CreateStock {

	ListStockDto execute (String productId);
}
