package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.books.Edition;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class EditionTest {

	@Test
	@DisplayName("Should create a valid edition")
	void shouldCreateValidEdition() {
		Edition edition = new Edition("First Edition");
		assertEquals("First Edition", edition.getEdition());
	}

	@Test
	@DisplayName("Should throw exception when edition is null")
	void shouldThrowExceptionWhenEditionIsNull() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> {
			new Edition(null);
		});
		assertEquals("Edition not be null", exception.getMessage());
	}

	@Test
	@DisplayName("Should throw exception when edition is empty or blank")
	void shouldThrowExceptionWhenEditionIsBlank() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> {
			new Edition("   ");
		});
		assertEquals("Edition not be null", exception.getMessage());
	}

	@Test
	@DisplayName("Should be equal when editions are the same")
	void shouldBeEqualWhenEditionIsSame() {
		Edition e1 = new Edition("Deluxe Edition");
		Edition e2 = new Edition("Deluxe Edition");

		assertEquals(e1, e2);
		assertEquals(e1.hashCode(), e2.hashCode());
	}

	@Test
	@DisplayName("Should not be equal when editions are different")
	void shouldNotBeEqualWhenEditionIsDifferent() {
		Edition e1 = new Edition("Limited Edition");
		Edition e2 = new Edition("Standard Edition");

		assertNotEquals(e1, e2);
	}

	@Test
	@DisplayName("Should return true when compared to itself")
	void shouldBeEqualToItself() {
		Edition edition = new Edition("Collector's Edition");

		assertEquals(edition, edition);
	}

	@Test
	@DisplayName("Should return false when compared to null")
	void shouldNotBeEqualToNull() {
		Edition edition = new Edition("Anniversary Edition");

		assertNotEquals(null, edition);
	}

	@Test
	@DisplayName("Should return false when compared to a different class")
	void shouldNotBeEqualToDifferentClass() {
		Edition edition = new Edition("Hardcover");
		Object obj = new Object();

		assertNotEquals(edition, obj);
	}
}
