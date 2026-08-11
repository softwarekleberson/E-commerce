package com.cleancode.ecommerce.replacement.domain.exception;

public class IllegalReplacementException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalReplacementException(String message) {
		super(message);
	}
}
