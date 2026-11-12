package com.cleancode.ecommerce.payment.application.service.voucher;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.service.voucher.contract.VoucherAfterUseService;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.voucher.domain.Discount;
import com.cleancode.ecommerce.voucher.domain.TypeVoucher;
import com.cleancode.ecommerce.voucher.domain.Voucher;
import com.cleancode.ecommerce.voucher.domain.repository.VoucherRepository;

public class VoucherAfterUseImpl implements VoucherAfterUseService {

	private final VoucherRepository repository;
	
	public VoucherAfterUseImpl(VoucherRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void applyVoucherToPurchase (String voucherId, BigDecimal totalAmount, String customerId, String message, TypeVoucher typeVoucher) {

		var voucher = repository.listSingleVoucher(voucherId)
				.orElseThrow(() -> new IllegalDomainException("Voucher not found"));

		Discount discount = voucher.discountAfterUse(totalAmount);
		
		if(discount.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
			Voucher newVoucher = new Voucher(customerId, message, typeVoucher, discount.getDiscount()); 
			repository.save(newVoucher);
		}
	}
}