package com.cleancode.ecommerce.stock.domain.exception;

public class IllegalStockException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalStockException(String message) {
		super(message);
	}
}
