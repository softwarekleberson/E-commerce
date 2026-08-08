package com.cleancode.ecommerce.cart.application.service.contract;

import com.cleancode.ecommerce.stock.domain.Stock;

public interface UpdateNewReservation {

	Stock creteNewReservation (Stock stock, int quantity, String customerId, String cartId);
}
