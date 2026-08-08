package com.cleancode.ecommerce.stock.domain.exception;

public class IllegalReservationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalReservationException(String message) {
		super(message);
	}
}
