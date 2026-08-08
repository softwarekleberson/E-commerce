package com.cleancode.ecommerce.customer.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.card.ExpirationDate;
import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

public class ExpirationDateTest {

	@Test
	void shouldCreateExpirationDateWhenValid() {
		LocalDate futureDate = LocalDate.now().plusMonths(1);
		ExpirationDate expirationDate = new ExpirationDate(futureDate);

		assertNotNull(expirationDate);
		assertEquals(futureDate, expirationDate.getExpirationDate());
	}

	@Test
	void shouldCreateExpirationDateWhenToday() {
		LocalDate today = LocalDate.now();
		ExpirationDate expirationDate = new ExpirationDate(today);

		assertNotNull(expirationDate);
		assertEquals(today, expirationDate.getExpirationDate());
	}

	@Test
	void shouldThrowExceptionWhenDateIsInPast() {
		LocalDate pastDate = LocalDate.now().minusDays(1);

		Exception exception = assertThrows(IllegalCardException.class, () -> {
			new ExpirationDate(pastDate);
		});

		assertEquals("Expiration date cannot be in the past", exception.getMessage());
	}
}
