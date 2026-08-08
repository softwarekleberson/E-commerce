package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.dtos.address.UpdateAddressDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateDelivery;
import com.cleancode.ecommerce.customer.domain.address.Delivery;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class UpdateDeliveryImpl implements UpdateDelivery{

	private final CustomerRepository repository;

	public UpdateDeliveryImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public ListCustomerDto execute(String email, String id, UpdateAddressDto dto) {
		Customer customer = repository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));				
		Delivery delivery = customer.findDeliveryById(id);
		delivery.update(dto.receiver(), dto.main(), dto.street(), dto.number(), dto.neighborhood(), dto.zipCode(), dto.observation(), dto.streetType(), dto.typeResidence(), dto.city(), dto.state(), dto.country(), dto.deliveryPhrase());
		
		repository.save(customer);
		return new ListCustomerDto(customer);
	}
}