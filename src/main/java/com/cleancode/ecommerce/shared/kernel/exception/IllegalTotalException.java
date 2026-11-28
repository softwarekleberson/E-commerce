package com.cleancode.ecommerce.shared.kernel.exception;

public class IllegalTotalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IllegalTotalException(String message) {
		super(message);
	}

}
