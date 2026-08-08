package com.cleancode.ecommerce.cart.application.service.contract;

import com.cleancode.ecommerce.cart.application.dtos.input.CartUpdateStockResult;

public interface CartUpdateIntegrationService {
	String resolveCustomerIdByEmail(String email);
    CartUpdateStockResult cycleProductReservation(String currentReservationId, int newQuantity, String customerId, String cartId);
}
