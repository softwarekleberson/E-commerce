package com.cleancode.ecommerce.payment.application.service.contract;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.dto.GatewayResponse;

public interface PaymentGatewayClient {
	GatewayResponse authorize(String cardNumber, BigDecimal amount);
}
