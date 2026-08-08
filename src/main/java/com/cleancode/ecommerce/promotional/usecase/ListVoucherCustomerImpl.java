package com.cleancode.ecommerce.promotional.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.promotional.domain.Voucher;
import com.cleancode.ecommerce.promotional.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.promotional.usecase.contract.ListVoucherCustomer;
import com.cleancode.ecommerce.promotional.usecase.dto.ListVoucherDto;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class ListVoucherCustomerImpl implements ListVoucherCustomer {

	private final VoucherRepository repository;
	private final CustomerRepository customerRepository;

	public ListVoucherCustomerImpl(VoucherRepository repository, CustomerRepository customerRepository) {
		this.repository = repository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Page<ListVoucherDto> execute(String email, Pageable pageable) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		Page<Voucher> vouchers = repository.listAllVouche(customer.getId().getValue(), pageable);

		return vouchers.map(ListVoucherDto::new);
	}
}
