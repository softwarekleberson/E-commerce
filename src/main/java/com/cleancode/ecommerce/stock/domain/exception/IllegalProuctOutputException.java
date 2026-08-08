package com.cleancode.ecommerce.stock.domain.exception;

public class IllegalProuctOutputException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalProuctOutputException(String message) {
		super(message);
	}
}
