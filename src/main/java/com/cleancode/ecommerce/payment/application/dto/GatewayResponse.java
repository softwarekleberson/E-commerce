package com.cleancode.ecommerce.payment.application.dto;

public record GatewayResponse(
		boolean isApproved,
		String transactionId,
		String declineReason){
}
