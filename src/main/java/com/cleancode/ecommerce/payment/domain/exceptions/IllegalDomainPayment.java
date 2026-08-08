package com.cleancode.ecommerce.payment.domain.exceptions;

public class IllegalDomainPayment extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public IllegalDomainPayment(String message) {
		super(message);
	}

}
