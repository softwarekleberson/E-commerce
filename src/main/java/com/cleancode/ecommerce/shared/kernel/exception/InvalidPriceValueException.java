package com.cleancode.ecommerce.shared.kernel.exception;

public class InvalidPriceValueException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidPriceValueException(String message) {
		super(message);
	}

}
