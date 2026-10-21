package com.cleancode.ecommerce.shared.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.cleancode.ecommerce.shared.config.security.JwtService;

import io.jsonwebtoken.JwtException;

class JwtServiceTest {

	private JwtService jwtService;
	private UserDetails customer;

	@BeforeEach
	void setUp() {
		String testSecret = Base64.getEncoder().encodeToString(
				"test-signing-key-with-at-least-32-bytes".getBytes(StandardCharsets.UTF_8));
		jwtService = new JwtService(testSecret, 60_000);
		customer = User.withUsername("customer@example.test").password("unused").roles("CUSTOMER").build();
	}

	@Test
	void generatedTokenContainsSubjectAndIsValidForItsOwner() {
		String token = jwtService.generateToken(customer);

		assertEquals(customer.getUsername(), jwtService.extractUsername(token));
		assertTrue(jwtService.isTokenValid(token, customer));
	}

	@Test
	void tokenIsNotValidForAnotherUser() {
		String token = jwtService.generateToken(customer);
		UserDetails anotherCustomer = User.withUsername("other@example.test").password("unused").roles("CUSTOMER")
				.build();

		assertFalse(jwtService.isTokenValid(token, anotherCustomer));
	}

	@Test
	void malformedTokenIsRejected() {
		assertThrows(JwtException.class, () -> jwtService.extractUsername("not-a-jwt"));
	}
}
