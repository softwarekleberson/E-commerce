package com.cleancode.ecommerce.cart.domain.repository;

import java.util.Optional;

public interface CartReservationSettingsRepository {

	Optional<Integer> findMaxDurationMinutes();

	void saveMaxDurationMinutes(int minutes);
}
