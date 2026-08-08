package com.cleancode.ecommerce.order.application.usecase;

import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.payment.domain.repository.PaymentRepository;

public class CreateOrderImpl {

	private final CustomerRepository customerRepository;
	private final PaymentRepository paymentRepository;
	
	public CreateOrderImpl(CustomerRepository customerRepository, PaymentRepository paymentRepository) {
		this.customerRepository = customerRepository;
		this.paymentRepository = paymentRepository;
	}
	
	public void execute () {
		
	}
}
