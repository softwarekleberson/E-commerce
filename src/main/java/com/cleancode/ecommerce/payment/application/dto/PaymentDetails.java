package com.cleancode.ecommerce.payment.application.dto;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.domain.TypePayment;

public record PaymentDetails(
		
		TypePayment typePayment,
		
		String numberCardOne,
		BigDecimal amountCardOne,
		
		String numberCardTwo,
		BigDecimal amountCardTwo,
		
		String voucherId
							
							) {

}
