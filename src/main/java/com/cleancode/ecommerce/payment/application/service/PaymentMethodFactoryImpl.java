package com.cleancode.ecommerce.payment.application.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.service.contract.PaymentMethodFactory;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.TypePayment;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;


public class PaymentMethodFactoryImpl implements PaymentMethodFactory{

	private final Map<TypePayment, PaymentMethod> methods = new EnumMap<>(TypePayment.class);
	
	public PaymentMethodFactoryImpl(List<PaymentMethod> availableMethods) {
		availableMethods.forEach(method -> methods.put(method.getType(), method));
	}
	
	@Override
	public PaymentMethod create(PaymentDetails dto) {
		return Optional.ofNullable(methods.get(dto.typePayment()))
				.orElseThrow(() -> new IllegalDomainPayment("Method not suported"));
	}
}
