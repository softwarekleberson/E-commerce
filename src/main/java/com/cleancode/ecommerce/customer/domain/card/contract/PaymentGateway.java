package com.cleancode.ecommerce.customer.domain.card.contract;

import java.math.BigDecimal;

import com.cleancode.ecommerce.customer.domain.card.Card;

public interface PaymentGateway {
	boolean process(Card card, BigDecimal amount);
}
