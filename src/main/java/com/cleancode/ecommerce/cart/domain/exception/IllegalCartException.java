package com.cleancode.ecommerce.cart.domain.exception;

public class IllegalCartException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalCartException(String message) {
		super(message);
	}
}
