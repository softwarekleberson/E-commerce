package com.cleancode.ecommerce.event.product;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.product.application.usecase.contract.ActiveProduct;

@Component
public class ActiveProductEventListener {
	private final ActiveProduct useCase;
	
	public ActiveProductEventListener(ActiveProduct activeProduct) {
		this.useCase = activeProduct;
	}
	
	@EventListener
	public void onProductActivated(StockUpdatedEvent event) {
		useCase.execute(event.productId(), event.StockId());
	}
}
