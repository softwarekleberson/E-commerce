package com.cleancode.ecommerce.stock.application.usecase.contract;

import java.util.List;

import com.cleancode.ecommerce.stock.application.dto.ListStockDto;

public interface ListStocks {
	List<ListStockDto> execute();
}
