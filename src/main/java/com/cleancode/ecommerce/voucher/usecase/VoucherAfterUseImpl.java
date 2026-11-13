package com.cleancode.ecommerce.voucher.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.voucher.domain.TypeVoucher;
import com.cleancode.ecommerce.voucher.domain.Voucher;
import com.cleancode.ecommerce.voucher.usecase.service.contract.VoucherAfterUseService;

public class VoucherAfterUseImpl implements VoucherAfterUseService{

	@Override
	public Voucher applyVoucherToPurchase (String customerId, String message, TypeVoucher typeVoucher, BigDecimal discount) {

		Voucher voucher = new Voucher(customerId, message, typeVoucher, discount);
		return voucher;
	}
}