package com.cleancode.ecommerce.customer.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.card.NumberCard;
import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

public class NumberCardTest {

	@Test
	@DisplayName("Should create a valid NumberCard with 16 digits and a valid Luhn check")
	void shouldCreateValidCard() {
		// 4539578763621486 é um número válido (Visa de teste)
		NumberCard card = new NumberCard("4539578763621486");

		assertNotNull(card);
		assertEquals("4539578763621486", card.getNumberCard());
	}

	@Test
	@DisplayName("Should throw exception if number is null")
	void shouldThrowExceptionWhenCardIsNull() {
		IllegalCardException exception = assertThrows(IllegalCardException.class, () -> new NumberCard(null));
		assertEquals("Card number must have exactly 16 digits", exception.getMessage());
	}

	@Test
	@DisplayName("Should throw exception if number does not have 16 digits")
	void shouldThrowExceptionWhenCardHasWrongLength() {
		assertThrows(IllegalCardException.class, () -> new NumberCard("12345678")); // curto
		assertThrows(IllegalCardException.class, () -> new NumberCard("123456789012345678")); // longo
	}

	@Test
	@DisplayName("Should throw exception if number contains invalid characters")
	void shouldThrowExceptionWhenCardHasInvalidCharacters() {
		assertThrows(IllegalCardException.class, () -> new NumberCard("45395787636214AB"));
	}

	@Test
	@DisplayName("Should throw an exception if the number is invalid according to the Luhn algorithm")
	void shouldThrowExceptionWhenLuhnCheckFails() {
		// Mesmo número válido, mas alterando o último dígito para quebrar o Luhn
		IllegalCardException exception = assertThrows(IllegalCardException.class,
				() -> new NumberCard("4539578763621487") // inválido
		);
		assertEquals("Invalid card number (Luhn check failed)", exception.getMessage());
	}
}
