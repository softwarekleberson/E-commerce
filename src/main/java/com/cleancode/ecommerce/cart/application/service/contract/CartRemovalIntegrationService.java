package com.cleancode.ecommerce.cart.application.service.contract;

import com.cleancode.ecommerce.cart.application.service.dto.CancelReservationResult;

public interface CartRemovalIntegrationService {
	String resolveCustomerIdByEmail(String email);
    CancelReservationResult releaseStockReservation(String reservationId);
}
