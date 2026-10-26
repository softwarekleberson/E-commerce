package com.cleancode.ecommerce.event.product;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.product.infra.service.cache.ProductCacheService;

@Component
public class SellingPriceEventListener {

	private final ProductCacheService productCacheService;
	
	public SellingPriceEventListener(ProductCacheService productCacheService) {
		this.productCacheService = productCacheService;
	}
	
	@EventListener
	public void onProductActivated(StockUpdatedEvent event) {
		productCacheService.recalculatePrice(event.productId(), event.StockId());
	}
}
