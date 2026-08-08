package com.cleancode.ecommerce.promotional.usecase.service.contract;

import java.math.BigDecimal;

import com.cleancode.ecommerce.promotional.domain.TypeVoucher;
import com.cleancode.ecommerce.promotional.domain.Voucher;

public interface VoucherAfterUseService {

	public Voucher applyVoucherToPurchase (String customerId, String message, TypeVoucher typeVoucher, BigDecimal discount);
}
