package com.cleancode.ecommerce.stock.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.event.product.EventPublisher;
import com.cleancode.ecommerce.stock.application.usecase.AddProductStockImpl;
import com.cleancode.ecommerce.stock.application.usecase.CreateStockImpl;
import com.cleancode.ecommerce.stock.application.usecase.contract.AddProductStock;
import com.cleancode.ecommerce.stock.application.usecase.contract.CreateStock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

@Configuration
public class StockConfig {

	@Bean
	public CreateStock createStock(StockRepository stockRepository) {

		return new CreateStockImpl(stockRepository);
	}

	@Bean
	public AddProductStock CreateProductInput(
			StockRepository repository, EventPublisher eventPublisher) {

		return new AddProductStockImpl(repository, eventPublisher);
	}
}