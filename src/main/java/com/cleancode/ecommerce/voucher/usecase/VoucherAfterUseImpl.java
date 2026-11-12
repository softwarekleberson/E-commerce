package com.cleancode.ecommerce.promotional.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.promotional.domain.TypeVoucher;
import com.cleancode.ecommerce.promotional.domain.Voucher;
import com.cleancode.ecommerce.promotional.usecase.service.contract.VoucherAfterUseService;

public class VoucherAfterUseImpl implements VoucherAfterUseService{

	@Override
	public Voucher applyVoucherToPurchase (String customerId, String message, TypeVoucher typeVoucher, BigDecimal discount) {

		Voucher voucher = new Voucher(customerId, message, typeVoucher, discount);
		return voucher;
	}
}