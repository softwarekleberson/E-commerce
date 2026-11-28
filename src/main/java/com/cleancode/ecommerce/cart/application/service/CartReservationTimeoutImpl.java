package com.cleancode.ecommerce.cart.application.service;

import com.cleancode.ecommerce.cart.application.service.contract.CartReservationTimeout;
import com.cleancode.ecommerce.cart.domain.repository.CartReservationSettingsRepository;

public class CartReservationTimeoutImpl implements CartReservationTimeout {

	public static final int DEFAULT_MAX_DURATION_MINUTES = 30;

	private final CartReservationSettingsRepository repository;

	public CartReservationTimeoutImpl(CartReservationSettingsRepository repository) {
		this.repository = repository;
	}

	@Override
	public int getMaxDurationMinutes() {
		return repository.findMaxDurationMinutes().orElse(DEFAULT_MAX_DURATION_MINUTES);
	}

	@Override
	public void updateMaxDurationMinutes(int minutes) {
		if (minutes <= 0) {
			throw new IllegalArgumentException("Cart reservation duration must be positive");
		}

		repository.saveMaxDurationMinutes(minutes);
	}
}
