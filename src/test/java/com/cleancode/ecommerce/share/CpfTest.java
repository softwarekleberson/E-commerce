package com.cleancode.ecommerce.share;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.shared.kernel.domain.Cpf;
import com.cleancode.ecommerce.shared.kernel.exception.IllegalCpfException;

class CpfTest {

	@Test
	void shouldNotCreateInvalidCPF() {
	
		assertThrows(IllegalCpfException.class, () -> new Cpf(null));
		assertThrows(IllegalCpfException.class, () -> new Cpf(""));
		assertThrows(IllegalCpfException.class, () -> new Cpf("430.050.444 -900"));
	}
	
	@Test
	void shoudCreateCpf() {
		String validate = "412.036.555-60";
		Cpf cpf = new Cpf(validate);
		assertEquals(validate, cpf.getCpf());
	}
}
