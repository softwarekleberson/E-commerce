package com.cleancode.ecommerce.payment.application.dto;

public record CardAuthorizationResponse(
		boolean isApproved,
		String transactionId,
		String declineReason){
}
