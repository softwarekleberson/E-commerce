package com.cleancode.ecommerce.customer.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.card.PrintedName;
import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;

public class PrintedNameTest {

	@Test
	void shouldCreatePrintedNameWhenValidName() {
		String validName = "Kleberson Santos";
		PrintedName printedName = new PrintedName(validName);

		assertNotNull(printedName);
		assertEquals(validName, printedName.getName());
	}

	@Test
	void shouldThrowExceptionWhenNameIsNull() {
		Exception exception = assertThrows(IllegalCardException.class, () -> {
			new PrintedName(null);
		});

		assertEquals("Printed name is required in card", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenNameIsBlank() {
		Exception exception = assertThrows(IllegalCardException.class, () -> {
			new PrintedName("   ");
		});

		assertEquals("Printed name is required in card", exception.getMessage());
	}

	@Test
	void shouldBeEqualWhenNamesAreSame() {
		PrintedName name1 = new PrintedName("Kleberson");
		PrintedName name2 = new PrintedName("Kleberson");

		assertEquals(name1, name2);
		assertEquals(name1.hashCode(), name2.hashCode());
	}

	@Test
	void shouldNotBeEqualWhenNamesAreDifferent() {
		PrintedName name1 = new PrintedName("Kleberson");
		PrintedName name2 = new PrintedName("Silva");

		assertNotEquals(name1, name2);
	}

	@Test
	void shouldNotBeEqualToNullOrDifferentClass() {
		PrintedName name = new PrintedName("Kleberson");

		assertNotEquals(name, null);
		assertNotEquals(name, "Kleberson");
	}
}
