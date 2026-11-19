package com.cleancode.ecommerce.shared.exception;

public class IllegalCpfException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalCpfException(String message) {
		super(message);
	}
}
