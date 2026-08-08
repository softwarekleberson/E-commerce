package com.cleancode.ecommerce.product.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class MidiaTest {

	@Test
	void shouldCreateMidiaSucefull() {
		Media midia = new Media("https://example.com/image.jpg", "Test Description");

		assertTrue(midia.getId().matches("^[0-9a-fA-F\\-]{36}$"));
		assertEquals("https://example.com/image.jpg", midia.getUrl());
		assertEquals("Test Description", midia.getDescription());
	}

	@Test
	void shouldThrowExceptionWhenUrlIsInvalid() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class,
				() -> new Media("invalid-url", "Descrição válida"));
		assertEquals("Url not be valid", exception.getMessage());
	}

	@Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        IllegalDomainException exception = assertThrows(
            IllegalDomainException.class,
            () -> new Media("https://example.com/image.jpg", null)
        );
        assertEquals("Description not be valid", exception.getMessage());
    }

	@Test
	void shouldThrowExceptionWhenDescriptionIsBlank() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class,
				() -> new Media("https://example.com/image.jpg", "   "));
		assertEquals("Description not be valid", exception.getMessage());
	}
}
