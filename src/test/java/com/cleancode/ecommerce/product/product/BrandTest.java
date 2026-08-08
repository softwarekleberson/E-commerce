package com.cleancode.ecommerce.product.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class BrandTest {

	@Test
	void shouldCreateBrandSuccessfully() {
		Brand brand = new Brand("Nike");
		assertEquals("Nike", brand.getBrand());
	}

	@Test
	void shouldThrowExceptionWhenBrandIsNull() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Brand(null));
		assertEquals("Brand not be null", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenBrandIsEmpty() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Brand(""));
		assertEquals("Brand not be null", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenBrandIsBlank() {
		IllegalDomainException exception = assertThrows(IllegalDomainException.class, () -> new Brand("   "));
		assertEquals("Brand not be null", exception.getMessage());
	}
}
