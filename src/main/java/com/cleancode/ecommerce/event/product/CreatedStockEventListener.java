package com.cleancode.ecommerce.event.product;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.stock.application.usecase.contract.CreateStock;

@Component
public class CreatedStockEventListener {

	private final CreateStock useCase;
	
	public CreatedStockEventListener(CreateStock useCase) {
		this.useCase = useCase;
	}
	
	@EventListener
	public void onProductActivated(ProductEvent event) {
		useCase.execute(event.productId());
	}
}