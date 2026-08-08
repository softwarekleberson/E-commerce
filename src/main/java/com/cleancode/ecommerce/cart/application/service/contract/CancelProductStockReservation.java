package com.cleancode.ecommerce.cart.application.service.contract;

import com.cleancode.ecommerce.stock.domain.Stock;

public interface CancelProductStockReservation {

	Stock cancel(Stock stock, String reservationId);
}
