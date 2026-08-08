package com.cleancode.ecommerce.customer.infra.gateway;

import java.util.Optional;

import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.customer.infra.mapper.CustomerMapper;
import com.cleancode.ecommerce.customer.infra.persistence.customer.CustomerEntity;
import com.cleancode.ecommerce.promotional.usecase.service.contract.CustomerIdentityService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomerRepositoryJpa implements CustomerRepository, CustomerIdentityService {

	private final CustomerJpa jpa;

	public CustomerRepositoryJpa(CustomerJpa jpa) {
		this.jpa = jpa;
	}

	@Override
	@Transactional
	public void save(Customer customer) {
		Optional<CustomerEntity> optionalEntity = jpa.findFullById(customer.getId().getValue().toString());

		CustomerEntity entity;

		if (optionalEntity.isPresent()) {
			entity = CustomerMapper.toEntity(customer, optionalEntity.get());
		} else {
			entity = CustomerMapper.toEntity(customer);
		}

		jpa.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Customer> getCustomerById(String id) {
		return jpa.findFullById(id).map(CustomerMapper::toDomain);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Customer> getAllCustomers(Pageable pageable) {
		return jpa.findAllCustomer(pageable)
			.map(CustomerMapper::toDomain);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Customer> findByEmail(String email) {
	    return jpa.findByEmail_Email(email)
	              .map(CustomerMapper::toDomain);
	}

	@Override
	public boolean existsById(String customerId) {
		return jpa.existsById(customerId);
	}

}