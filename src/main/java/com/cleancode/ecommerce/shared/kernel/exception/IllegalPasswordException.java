package com.cleancode.ecommerce.shared.kernel.exception;

public class IllegalPasswordException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalPasswordException(String message) {
		super(message);
	}
}
