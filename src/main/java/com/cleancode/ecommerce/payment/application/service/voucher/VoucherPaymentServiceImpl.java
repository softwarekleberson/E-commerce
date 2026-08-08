package com.cleancode.ecommerce.payment.application.service.voucher;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.service.voucher.contract.VoucherAfterUseService;
import com.cleancode.ecommerce.payment.application.service.voucher.contract.VoucherPaymentService;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;
import com.cleancode.ecommerce.promotional.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class VoucherPaymentServiceImpl implements VoucherPaymentService {

	private final VoucherRepository repository;
	private final VoucherAfterUseService voucherAfterUseService;

	public VoucherPaymentServiceImpl(VoucherRepository repository, VoucherAfterUseService voucherAfterUseService) {
		this.repository = repository;
		this.voucherAfterUseService = voucherAfterUseService;
	}

	@Override
	public boolean voucherPayment(String voucherId, BigDecimal totalAmount) {
		
		if (voucherId == null || voucherId.isBlank()) {
			throw new IllegalDomainPayment("Voucher code must be provided");
		}
		
		var voucher = repository.listSingleVoucher(voucherId)
				.orElseThrow(() -> new IllegalDomainException("Voucher not found"));

		boolean applied = voucher.apply(totalAmount);
		
		if(applied) {
			repository.save(voucher);
			voucherAfterUseService.applyVoucherToPurchase(voucherId, totalAmount, voucher.getCustomerId().getValue(), voucher.getMessage().getMessage(), voucher.getTypeVoucher());
			return true;
		}
		
		return false;
	}

	@Override
	public BigDecimal valueVoucher(String voucherId) {
		if (voucherId == null || voucherId.isBlank()) {
			throw new IllegalDomainPayment("Voucher code must be provided");
		}
		
		var voucher = repository.listSingleVoucher(voucherId)
				.orElseThrow(() -> new IllegalDomainException("Voucher not found"));

		return voucher.getDiscount().getDiscount();
	}
}