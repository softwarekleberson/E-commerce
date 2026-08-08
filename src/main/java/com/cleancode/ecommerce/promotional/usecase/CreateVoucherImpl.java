package com.cleancode.ecommerce.promotional.usecase;

import com.cleancode.ecommerce.promotional.domain.Voucher;
import com.cleancode.ecommerce.promotional.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.promotional.usecase.contract.CreateVoucher;
import com.cleancode.ecommerce.promotional.usecase.dto.CreateVoucherDto;
import com.cleancode.ecommerce.promotional.usecase.service.contract.CustomerIdentityService;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class CreateVoucherImpl implements CreateVoucher {

	private final VoucherRepository voucherRepository;
	private final CustomerIdentityService customerIdentityService;
	
	public CreateVoucherImpl(VoucherRepository voucherRepository, CustomerIdentityService customerIdentityService) {
		this.voucherRepository = voucherRepository;
		this.customerIdentityService = customerIdentityService;
	}

	@Override
	public void execute(CreateVoucherDto dto) {		
		if(!this.customerIdentityService.existsById(dto.getCustomerId())) {
			throw new CustomerNotFoundException("Customer not found with id: " + dto.getCustomerId());
		}
		
		Voucher voucher = new Voucher(dto.getCustomerId(),dto.getMessage(), dto.getTypeVoucher(), dto.getDiscount());
		voucherRepository.save(voucher);
	}
}