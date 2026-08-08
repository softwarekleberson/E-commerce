package com.cleancode.ecommerce.payment.application.service.voucher.contract;

import java.math.BigDecimal;

public interface VoucherPaymentService {

	public boolean voucherPayment(String voucherId, BigDecimal totalAlmount);
	public BigDecimal valueVoucher(String voucherId);
}
