package com.cleancode.ecommerce.payment.domain.repository;

import java.util.Optional;

import com.cleancode.ecommerce.payment.domain.Payment;

public interface PaymentRepository {

	void save (Payment payment);
	Optional<Payment> getPaymentId(String paymentId);
}
