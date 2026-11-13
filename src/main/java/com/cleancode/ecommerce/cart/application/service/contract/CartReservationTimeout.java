package com.cleancode.ecommerce.cart.application.service.contract;

public interface CartReservationTimeout {

	int getMaxDurationMinutes();

	void updateMaxDurationMinutes(int minutes);
}
