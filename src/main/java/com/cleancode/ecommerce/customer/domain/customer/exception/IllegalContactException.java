package com.cleancode.ecommerce.customer.domain.customer.exception;

public class IllegalContactException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalContactException(String message) {
		super(message);
	}
}
