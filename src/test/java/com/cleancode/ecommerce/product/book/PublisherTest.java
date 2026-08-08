package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.books.PublisherDate;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class PublisherTest {

	@Test
	@DisplayName("Should create a valid publisher date (past date)")
	void shouldCreateValidPastDate() {
		LocalDate pastDate = LocalDate.of(2020, 5, 10);
		PublisherDate publisherDate = new PublisherDate(pastDate);

		assertEquals(pastDate, publisherDate.getPublisherDate());
	}

	@Test
	@DisplayName("Should create a valid publisher date (today's date)")
	void shouldCreateValidTodayDate() {
		LocalDate today = LocalDate.now();
		PublisherDate publisherDate = new PublisherDate(today);

		assertEquals(today, publisherDate.getPublisherDate());
	}

	@Test
	@DisplayName("Should throw exception when publisher date is in the future")
	void shouldThrowExceptionForFutureDate() {
		LocalDate futureDate = LocalDate.now().plusDays(1);

		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> {
			new PublisherDate(futureDate);
		});

		assertEquals("Date publisher Invalid", exception.getMessage());
	}

	@Test
	@DisplayName("Should be equal when publisher dates are the same")
	void shouldBeEqualWhenDatesAreSame() {
		LocalDate date = LocalDate.of(2022, 1, 1);
		PublisherDate d1 = new PublisherDate(date);
		PublisherDate d2 = new PublisherDate(date);

		assertEquals(d1, d2);
		assertEquals(d1.hashCode(), d2.hashCode());
	}

	@Test
	@DisplayName("Should not be equal when publisher dates are different")
	void shouldNotBeEqualWhenDatesAreDifferent() {
		PublisherDate d1 = new PublisherDate(LocalDate.of(2022, 1, 1));
		PublisherDate d2 = new PublisherDate(LocalDate.of(2023, 1, 1));

		assertNotEquals(d1, d2);
	}

	@Test
	@DisplayName("Should return true when comparing to itself")
	void shouldBeEqualToItself() {
		PublisherDate date = new PublisherDate(LocalDate.of(2022, 3, 10));

		assertEquals(date, date);
	}

	@Test
	@DisplayName("Should return false when comparing to null")
	void shouldNotBeEqualToNull() {
		PublisherDate date = new PublisherDate(LocalDate.of(2022, 3, 10));

		assertNotEquals(null, date);
	}

	@Test
	@DisplayName("Should return false when comparing to an object of a different class")
	void shouldNotBeEqualToDifferentClass() {
		PublisherDate date = new PublisherDate(LocalDate.of(2022, 3, 10));
		Object obj = new Object();

		assertNotEquals(date, obj);
	}
}
