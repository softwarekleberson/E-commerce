package com.cleancode.ecommerce.payment.application.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentGatewayClient;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.TypePayment;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;

public class CardPayment implements PaymentMethod {

	private final PaymentGatewayClient gateway;

	public CardPayment(PaymentGatewayClient gateway) {
		this.gateway = gateway;
	}

	@Override
	public PaymentExecutionResult payment(BigDecimal totalAmount, PaymentDetails dto) {

		validateDto(totalAmount, dto);

		try {
			var response = gateway.authorize(dto.numberCardOne(), totalAmount);
			if (response.isApproved()) {
				return new PaymentExecutionResult(true, "Credit Card", null);
			}

			return new PaymentExecutionResult(false, null, response.declineReason());

		} catch (Exception e) {
			return new PaymentExecutionResult(false, null, "Payment gateway unavailable. Please try again later.");
		}
	}

	private void validateDto(BigDecimal totalAmount, PaymentDetails dto) {
		if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalDomainPayment("Invalid payment amount");
		}

		if (dto.numberCardOne() == null || dto.numberCardOne().isBlank()) {
			throw new IllegalDomainPayment("Card number must be provided");
		}
	}

	@Override
	public TypePayment getType() {
		return TypePayment.CARD;
	}
}