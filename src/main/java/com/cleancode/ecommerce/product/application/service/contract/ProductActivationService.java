package com.cleancode.ecommerce.product.application.service.contract;

import com.cleancode.ecommerce.product.domain.Product;

public interface ProductActivationService {

	public Product activateProductIfStockAvailable (String productId, String StockId);
}
