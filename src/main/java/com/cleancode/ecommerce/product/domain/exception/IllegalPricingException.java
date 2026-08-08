package com.cleancode.ecommerce.product.domain.exception;

public class IllegalPricingException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalPricingException(String message) {
		super(message);
	}
}
