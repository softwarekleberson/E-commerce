package com.cleancode.ecommerce.share;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Name;

public class NameTest {

	@Test
	void sholdCreateNameSuccessfully() {
		Name name = new Name("kleberson");
		assertEquals("kleberson", name.getName());
	}

	@Test
	void shouldThrowExceptionWhenNameIsNull() {
		IllegalDomainException exception = assertThrows
		(IllegalDomainException.class, () -> new Name(null));
		
		assertEquals("Name not be null", exception.getMessage());
	}
	
	@Test
	void shoudThrowExceptionWhenNameIsEmpty() {
		IllegalDomainException exception = assertThrows
		(IllegalDomainException.class, () -> new Name(""));
		
		assertEquals("Name not be null", exception.getMessage());
	}
	
	@Test
	void shoudThrowExceptionWhenNameIsBlank() {
		IllegalDomainException exception = assertThrows
		(IllegalDomainException.class, () -> new Name("  "));
		
		assertEquals("Name not be null", exception.getMessage());
	}
}
