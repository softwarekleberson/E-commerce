package com.cleancode.ecommerce.cart.infra.gateway;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.cart.domain.repository.CartReservationSettingsRepository;
import com.cleancode.ecommerce.cart.infra.persistence.CartReservationSettingsEntity;

@Repository
public class CartReservationSettingsRepositoryJpa implements CartReservationSettingsRepository {

	private static final int SETTINGS_ID = 1;

	private final CartReservationSettingsJpa jpa;

	public CartReservationSettingsRepositoryJpa(CartReservationSettingsJpa jpa) {
		this.jpa = jpa;
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Integer> findMaxDurationMinutes() {
		return jpa.findById(SETTINGS_ID).map(CartReservationSettingsEntity::getMax_duration_minutes);
	}

	@Transactional
	@Override
	public void saveMaxDurationMinutes(int minutes) {
		CartReservationSettingsEntity entity = jpa.findById(SETTINGS_ID)
				.orElseGet(() -> new CartReservationSettingsEntity(SETTINGS_ID, minutes));
		entity.setMax_duration_minutes(minutes);
		jpa.save(entity);
	}
}
