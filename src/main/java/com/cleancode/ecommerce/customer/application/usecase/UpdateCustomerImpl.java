package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.UpdateCustomerDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateCustomer;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class UpdateCustomerImpl implements UpdateCustomer {

	private final CustomerRepository repository;

	public UpdateCustomerImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public ListCustomerDto execute(String email, UpdateCustomerDto dto) {
		Customer customer = repository.findByEmail(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		customer.updateCustomer(dto.name(), dto.birth(), dto.ddd(), dto.phone(), dto.typePhone());
		repository.save(customer);
		return new ListCustomerDto(customer);
	}
}