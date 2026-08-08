package com.cleancode.ecommerce.payment.infra.fake.payment;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.payment.application.dto.CardAuthorizationResponse;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentGatewayClient;

@Component
public class FakeGatewayClient implements PaymentGatewayClient{

	private final BigDecimal TESTE_LIMITE_FAKE = new BigDecimal("50"); 
	
	@Override
	public CardAuthorizationResponse authorize(String cardNumber, BigDecimal amount) {
		if(amount.compareTo(TESTE_LIMITE_FAKE) >= 0) {
			return new CardAuthorizationResponse(true, "Fake Transaction", null);
		}
		return new CardAuthorizationResponse(false, null, "Card without credit");
	}

	@Override
	public void refund(String transactionIdOrCardNumber, BigDecimal amount) {
		System.out.println("The card " + transactionIdOrCardNumber + " with amount " + amount + "not be processed your card");
	}
}