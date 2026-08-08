package com.cleancode.ecommerce.payment.application.dto;

public record PaymentExecutionResult(
		
		boolean success, 
	    String providerName,
	    String errorMessage
		
		) {
}
