package com.cleancode.ecommerce.order.domain.exceptions;

public class IllegalDomainOrder extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public IllegalDomainOrder(String message) {
		super(message);
	}
}
