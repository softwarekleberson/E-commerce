package com.cleancode.ecommerce.stock.application.usecase.contract;

import com.cleancode.ecommerce.stock.application.dto.CreateInputStockDto;
import com.cleancode.ecommerce.stock.application.dto.ListStockDto;

public interface AddProductStock {

	ListStockDto execute (CreateInputStockDto dto);
}
