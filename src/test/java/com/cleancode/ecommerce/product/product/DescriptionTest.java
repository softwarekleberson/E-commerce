package com.cleancode.ecommerce.product.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class DescriptionTest {

	@Test
	void shouldCreateDescriptionSuccessfully() {
		Description description = new Description("New Product and origin");
		assertEquals("New Product and origin", description.getDescription());
	}

	@Test
	void shouldThrowExceptionWhenDescriptionIsNull() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Description(null));
		assertEquals("Description not be null", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenDescriptionIsEmpty() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Description(""));
		assertEquals("Description not be null", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenDescriptionIsBlank() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Description("   "));
		assertEquals("Description not be null", exception.getMessage());
	}
}