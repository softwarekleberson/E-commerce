package com.cleancode.ecommerce.adm.domain.exception;

public class IllegalAdmException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalAdmException(String message) {
		super(message);
	}
}
