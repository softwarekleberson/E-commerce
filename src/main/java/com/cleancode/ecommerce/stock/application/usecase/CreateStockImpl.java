package com.cleancode.ecommerce.stock.application.usecase;

import com.cleancode.ecommerce.stock.application.dto.ListStockDto;
import com.cleancode.ecommerce.stock.application.usecase.contract.CreateStock;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class CreateStockImpl implements CreateStock {

	private final StockRepository stockRepository;

	public CreateStockImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Override
	public ListStockDto execute(String productId) {
		
		Stock stock = new Stock(productId);
		stockRepository.save(stock);
		
		return new ListStockDto(stock);
	}
}