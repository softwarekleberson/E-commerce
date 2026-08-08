package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.usecase.contract.ChangeActivationStatusAdm;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class ChangeActivationStatusAdmImpl implements ChangeActivationStatusAdm {

	private final CustomerRepository repository;

	public ChangeActivationStatusAdmImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute(String customerId) {
		Customer customer = repository.getCustomerById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with id : " + customerId + " not found"));

		customer.changeCustomerActivationStatusImpl();
		repository.save(customer);
	}
}