package com.cleancode.ecommerce.cart.application.service.contract;

import com.cleancode.ecommerce.cart.application.service.dto.ContextDetailsDto;
import com.cleancode.ecommerce.cart.application.service.dto.ProductReservationResult;

public interface CartCatalogIntegrationService {
	ContextDetailsDto resolveContextDetails(String email, String productId);
    ProductReservationResult reserveStock(String productId, int quantity, String customerId, String cartId);
}
