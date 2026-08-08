package com.cleancode.ecommerce.product.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cleancode.ecommerce.product.domain.books.Isbn;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class IsbnTest {

	@Test
	@DisplayName("Should create a valid 10-digit ISBN")
	void shouldCreateValid10DigitIsbn() {
		Isbn isbn = new Isbn("123456789X");
		assertEquals("123456789X", isbn.getIsbn());
	}

	@Test
	@DisplayName("Should create a valid 13-digit ISBN starting with 978 or 979")
	void shouldCreateValid13DigitIsbn() {
		Isbn isbn = new Isbn("9781234567890");
		assertEquals("9781234567890", isbn.getIsbn());

		Isbn isbn2 = new Isbn("9791234567890");
		assertEquals("9791234567890", isbn2.getIsbn());
	}

	@ParameterizedTest
	@ValueSource(strings = { 
			"123456789", // 9 digits
			"ABCDEFGHIJ", // letters
			"978123456789", // only 12 digits
			"1234567890X", // too long
			"", // empty
			" ", // blank
			"97812345678900", // too many digits
			"12345X7890", // X not at the end
			"979123456789", // too short
			"978-1234567890" // contains dash
	})
	@DisplayName("Should throw exception for invalid ISBNs")
	void shouldThrowExceptionForInvalidIsbn(String invalidIsbn) {
		assertThrows(IllegalDomainException.class, () -> new Isbn(invalidIsbn));
	}

	@Test
	@DisplayName("Should consider two ISBNs equal when the strings match")
	void shouldBeEqualWhenIsbnMatches() {
		Isbn i1 = new Isbn("9781234567890");
		Isbn i2 = new Isbn("9781234567890");

		assertEquals(i1, i2);
		assertEquals(i1.hashCode(), i2.hashCode());
	}

	@Test
	@DisplayName("Should not be equal when ISBNs are different")
	void shouldNotBeEqualWhenIsbnIsDifferent() {
		Isbn i1 = new Isbn("9781234567890");
		Isbn i2 = new Isbn("9791234567890");

		assertNotEquals(i1, i2);
	}

	@Test
	@DisplayName("Should return true when comparing to itself")
	void shouldBeEqualToItself() {
		Isbn isbn = new Isbn("123456789X");

		assertEquals(isbn, isbn);
	}

	@Test
	@DisplayName("Should return false when comparing to null")
	void shouldNotBeEqualToNull() {
		Isbn isbn = new Isbn("123456789X");

		assertNotEquals(null, isbn);
	}

	@Test
	@DisplayName("Should return false when comparing to a different class")
	void shouldNotBeEqualToDifferentClass() {
		Isbn isbn = new Isbn("123456789X");
		Object other = new Object();

		assertNotEquals(isbn, other);
	}
}
