package com.cleancode.ecommerce.shared.kernel.exception;

public class IllegalDomainException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalDomainException(String message) {
		super(message);
	}
}
