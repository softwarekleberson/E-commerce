package com.cleancode.ecommerce.cart.application.service;

import com.cleancode.ecommerce.cart.application.service.contract.CancelProductStockReservation;
import com.cleancode.ecommerce.stock.domain.Stock;

public class CancelProductStockReservationImpl implements CancelProductStockReservation{

	@Override
    public Stock cancel(Stock stock, String reservationId) {
        stock.cancelReservation(reservationId);
        return stock;
    }
}
