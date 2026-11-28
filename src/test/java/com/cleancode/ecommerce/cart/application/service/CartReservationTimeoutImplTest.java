package com.cleancode.ecommerce.cart.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleancode.ecommerce.cart.domain.repository.CartReservationSettingsRepository;

@ExtendWith(MockitoExtension.class)
class CartReservationTimeoutImplTest {

	@Mock
	private CartReservationSettingsRepository repository;

	@Test
	void defaultsToThirtyMinutesWhenNoAdminSettingExists() {
		when(repository.findMaxDurationMinutes()).thenReturn(Optional.empty());

		assertThat(new CartReservationTimeoutImpl(repository).getMaxDurationMinutes()).isEqualTo(30);
	}

	@Test
	void returnsAndPersistsTheAdminConfiguredDuration() {
		when(repository.findMaxDurationMinutes()).thenReturn(Optional.of(45));
		CartReservationTimeoutImpl timeout = new CartReservationTimeoutImpl(repository);

		assertThat(timeout.getMaxDurationMinutes()).isEqualTo(45);

		timeout.updateMaxDurationMinutes(60);

		verify(repository).saveMaxDurationMinutes(60);
	}

	@Test
	void rejectsNonPositiveDurations() {
		CartReservationTimeoutImpl timeout = new CartReservationTimeoutImpl(repository);

		assertThatThrownBy(() -> timeout.updateMaxDurationMinutes(0))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Cart reservation duration must be positive");
	}
}
