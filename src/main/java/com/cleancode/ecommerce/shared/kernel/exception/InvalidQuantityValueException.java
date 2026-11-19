package com.cleancode.ecommerce.shared.kernel.exception;

public class InvalidQuantityValueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidQuantityValueException(String message) {
		super(message);
	}
}
