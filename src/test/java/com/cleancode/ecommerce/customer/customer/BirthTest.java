package com.cleancode.ecommerce.customer.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.customer.domain.customer.Birth;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

class BirthTest {

	@Test
	void ShouldnotCreateMinorClient() {
	
		assertThrows(IllegalDomainException.class, () -> new Birth(null));
		assertThrows(IllegalDomainException.class, () -> new Birth(LocalDate.now().minusYears(17)));
	}
	
	@Test
	void ShouldCreateAnAdultClient() {
		LocalDate validBirth = LocalDate.now().minusYears(18);
		Birth birth = new Birth(validBirth);

		assertEquals(validBirth, birth.getBirth());
	}
}
