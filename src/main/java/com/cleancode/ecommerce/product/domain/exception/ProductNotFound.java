package com.cleancode.ecommerce.product.domain.exception;

public class ProductNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProductNotFound(String message) {
		super(message);
	}
}
