package com.cleancode.ecommerce.payment.application.service.payment.contract;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.dto.CardAuthorizationResponse;

public interface PaymentGatewayClient {
	
	CardAuthorizationResponse authorize(String cardNumber, BigDecimal amount);
	void refund(String transactionIdOrCardNumber, BigDecimal amount);
}
