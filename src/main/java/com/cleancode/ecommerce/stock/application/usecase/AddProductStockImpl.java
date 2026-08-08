package com.cleancode.ecommerce.stock.application.usecase;

import com.cleancode.ecommerce.event.product.EventPublisher;
import com.cleancode.ecommerce.event.product.StockUpdatedEvent;
import com.cleancode.ecommerce.shared.exception.StockNotFound;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.stock.application.dto.CreateInputStockDto;
import com.cleancode.ecommerce.stock.application.dto.ListStockDto;
import com.cleancode.ecommerce.stock.application.usecase.contract.AddProductStock;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class AddProductStockImpl implements AddProductStock {

	private final StockRepository repository;
	private final EventPublisher eventPublisher;
	
	public AddProductStockImpl(StockRepository repository, EventPublisher eventPublisher) {
		this.repository = repository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public ListStockDto execute (CreateInputStockDto dto) {
		Stock stock = repository.getStock(dto.getProductId()).orElseThrow(() -> new StockNotFound("Stock with id:" + dto.getProductId() + " not found "));
		stock.addProductInput(dto.getQuantity(), dto.getProductQuality(), new Price(dto.getPurchasePrice(), dto.getCoin()), dto.getSupplier());

		repository.save(stock);
		
		eventPublisher.publish(new StockUpdatedEvent(dto.getProductId(), stock.getStockId().getStockId()));
		
		return new ListStockDto(stock);
	}
}