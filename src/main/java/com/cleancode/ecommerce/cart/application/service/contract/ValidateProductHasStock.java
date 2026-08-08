package com.cleancode.ecommerce.cart.application.service.contract;

import com.cleancode.ecommerce.cart.application.service.dto.ReservationResultDto;
import com.cleancode.ecommerce.stock.domain.Stock;

public interface ValidateProductHasStock {

	public ReservationResultDto reserve (Stock stock, int quantity, String customerId, String cartId);
}
