package com.cleancode.ecommerce.voucher.usecase.service.contract;

import java.math.BigDecimal;

import com.cleancode.ecommerce.voucher.domain.TypeVoucher;
import com.cleancode.ecommerce.voucher.domain.Voucher;

public interface VoucherAfterUseService {

	public Voucher applyVoucherToPurchase (String customerId, String message, TypeVoucher typeVoucher, BigDecimal discount);
}
