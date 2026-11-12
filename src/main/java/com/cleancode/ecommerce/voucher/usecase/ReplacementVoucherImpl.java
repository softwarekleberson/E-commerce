package com.cleancode.ecommerce.voucher.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;
import com.cleancode.ecommerce.voucher.domain.TypeVoucher;
import com.cleancode.ecommerce.voucher.domain.Voucher;
import com.cleancode.ecommerce.voucher.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.voucher.usecase.contract.ReplacementVoucher;
import com.cleancode.ecommerce.voucher.usecase.service.contract.CustomerIdentityService;

public class ReplacementVoucherImpl implements ReplacementVoucher{

	private final VoucherRepository repository;
	private final CustomerIdentityService service;
	
	public ReplacementVoucherImpl(VoucherRepository repository, CustomerIdentityService service) {
		this.repository = repository;
		this.service = service;
	}

	@Override
	public void execute (String customerId, BigDecimal value) {
		
		if(!this.service.existsById(customerId)) {
			throw new CustomerNotFoundException("Customer not found with id: " + customerId);
		}
		
		Voucher voucher = new Voucher
						(customerId,
						 "Exchange successfully accepted",
						 TypeVoucher.REPLACEMENT,
						 value
						 );
		
		repository.save(voucher);
	}
}