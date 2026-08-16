package com.cleancode.ecommerce.dash.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cleancode.ecommerce.payment.domain.Payment;
import com.cleancode.ecommerce.payment.domain.StatusPayment;
import com.cleancode.ecommerce.payment.domain.TypePayment;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public record PaymentDto(
		
		String paymentId,
	    String customerId,
	    LocalDateTime paymentDate,
	    BigDecimal total,
	    TypeCoin typeCoin,
	    StatusPayment statusPayment,
	    TypePayment typePayment
		
		) {
	
	public PaymentDto(Payment p) {
		this(p.getPaymentId().getId(), p.getCustomerId().getValue(), p.getPaymentDate(), p.getTotal().getTotalValue(), p.getTotal().getTypeCoin(),p.getStatusPayment(), p.getDescription().getFirst().getTypePayment());
	}
}
