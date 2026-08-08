package com.cleancode.ecommerce.stock.domain.exception;

public class IllegalProductInputException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalProductInputException(String message) {
		super(message);
	}
}
