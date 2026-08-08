package com.cleancode.ecommerce.payment.application.service.payment.contract;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;

public interface PaymentMethodFactory {

	public PaymentMethod create(PaymentDetails dto);
}
