package com.cleancode.ecommerce.product.domain.exception;

public class IllegalDimensionException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalDimensionException(String message) {
		super(message);
	}
}
