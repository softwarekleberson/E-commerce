package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.books.Synopsis;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class SynopsisTest {

	@Test
	void shouldCreateBrandSuccessfully() {
		Synopsis synopsis = new Synopsis("Este livro é legal");
		assertEquals("Este livro é legal", synopsis.getSynopsis());
	}

	@Test
	void shouldThrowExceptionWhenSynopsisIsNull() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Synopsis(null));
		assertEquals("Synopsis not be null", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenSynopsisIsEmpty() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Synopsis(""));
		assertEquals("Synopsis not be null", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenSynopsisIsBlank() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Synopsis("  "));
		assertEquals("Synopsis not be null", exception.getMessage());
	}
}