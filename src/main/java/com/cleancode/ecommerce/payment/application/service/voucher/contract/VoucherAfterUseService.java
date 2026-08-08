package com.cleancode.ecommerce.payment.application.service.voucher.contract;

import java.math.BigDecimal;

import com.cleancode.ecommerce.promotional.domain.TypeVoucher;

public interface VoucherAfterUseService {

	public void applyVoucherToPurchase (String voucherId, BigDecimal totalAmount, String customerId, String message, TypeVoucher typeVoucher);
}
