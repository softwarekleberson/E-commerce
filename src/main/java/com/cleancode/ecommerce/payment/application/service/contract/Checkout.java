package com.cleancode.ecommerce.payment.application.service.contract;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;

public interface Checkout {

	void execute(String email, PaymentDetails dto);
}
