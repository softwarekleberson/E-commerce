package com.cleancode.ecommerce.stock.application.usecase;

import java.util.List;

import com.cleancode.ecommerce.stock.application.dto.ListStockDto;
import com.cleancode.ecommerce.stock.application.usecase.contract.ListStocks;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class ListStocksImpl implements ListStocks {

	private final StockRepository stockRepository;

	public ListStocksImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Override
	public List<ListStockDto> execute() {
		return stockRepository.findAll().stream().map(ListStockDto::new).toList();
	}
}
