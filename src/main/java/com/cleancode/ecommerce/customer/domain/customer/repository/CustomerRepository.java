package com.cleancode.ecommerce.customer.domain.customer.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.customer.domain.customer.Customer;

public interface CustomerRepository {

	void save (Customer customer);
	Optional<Customer> getCustomerById(String id);
	Page<Customer> getAllCustomers(Pageable pageable);
	Optional<Customer> findByEmail(String email);

}
