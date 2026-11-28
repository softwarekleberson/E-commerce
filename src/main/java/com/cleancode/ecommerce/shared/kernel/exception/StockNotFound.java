package com.cleancode.ecommerce.shared.kernel.exception;

public class StockNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public StockNotFound(String message) {
		super(message);
	}
}
