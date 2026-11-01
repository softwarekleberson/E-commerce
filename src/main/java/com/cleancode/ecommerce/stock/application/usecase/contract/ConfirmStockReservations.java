package com.cleancode.ecommerce.stock.application.usecase.contract;

import java.util.Collection;

import com.cleancode.ecommerce.stock.application.dto.StockReservationConfirmation;

public interface ConfirmStockReservations {

	void execute(Collection<StockReservationConfirmation> confirmations);
}
