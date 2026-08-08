package com.cleancode.ecommerce.payment.application.usecase.contract;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.domain.TypePayment;

public interface PaymentMethod {
	PaymentExecutionResult payment (BigDecimal totalAmount, PaymentDetails dto);
	TypePayment getType();
}
