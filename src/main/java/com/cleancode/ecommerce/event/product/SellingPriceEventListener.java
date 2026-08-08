package com.cleancode.ecommerce.event.product;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.product.application.usecase.contract.SellingPriceToInventory;

@Component
public class SellingPriceEventListener {

	private final SellingPriceToInventory useCase;
	
	public SellingPriceEventListener(SellingPriceToInventory sellingPriceToInventory) {
		this.useCase = sellingPriceToInventory;
	}
	
	@EventListener
	public void onProductActivated(StockUpdatedEvent event) {
		useCase.execute(event.productId(), event.StockId());
	}
}