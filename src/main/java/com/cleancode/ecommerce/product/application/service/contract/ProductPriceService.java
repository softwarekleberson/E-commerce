package com.cleancode.ecommerce.product.application.service.contract;

import com.cleancode.ecommerce.product.domain.Product;

public interface ProductPriceService {

	Product productPriceService (String productId, String stockId);
}
