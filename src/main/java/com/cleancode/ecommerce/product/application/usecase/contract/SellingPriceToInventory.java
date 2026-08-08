package com.cleancode.ecommerce.product.application.usecase.contract;

public interface SellingPriceToInventory {

	public void execute (String productId, String stockId);
}
