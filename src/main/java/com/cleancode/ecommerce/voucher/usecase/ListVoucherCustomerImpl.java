package com.cleancode.ecommerce.voucher.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.shared.kernel.exception.CustomerNotFoundException;
import com.cleancode.ecommerce.shared.kernel.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.voucher.domain.Voucher;
import com.cleancode.ecommerce.voucher.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.voucher.usecase.contract.ListVoucherCustomer;
import com.cleancode.ecommerce.voucher.usecase.dto.ListVoucherDto;

public class ListVoucherCustomerImpl implements ListVoucherCustomer {

	private final VoucherRepository repository;
	private final CustomerIdentityIdService customerIdentityIdService;

	public ListVoucherCustomerImpl(VoucherRepository repository, CustomerIdentityIdService customerIdentityIdService) {
		this.repository = repository;
		this.customerIdentityIdService = customerIdentityIdService;
	}

	@Override
	public Page<ListVoucherDto> execute(String email, Pageable pageable) {
		String customerId = customerIdentityIdService.customerIdentityIdService(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		Page<Voucher> vouchers = repository.listAllVouche(customerId, pageable);

		return vouchers.map(ListVoucherDto::new);
	}
}
