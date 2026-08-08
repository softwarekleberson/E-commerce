package com.cleancode.ecommerce.promotional.domain.exception;

public class IllegalVoucherException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalVoucherException(String message) {
		super(message);
	}
}
