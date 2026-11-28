package com.cleancode.ecommerce.shared.kernel.exception;

public class CustomerNotActivedExcepetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotActivedExcepetion(String message) {
		super(message);
	}
}
