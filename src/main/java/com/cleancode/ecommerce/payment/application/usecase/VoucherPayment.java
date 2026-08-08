package com.cleancode.ecommerce.payment.application.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.application.service.voucher.contract.VoucherPaymentService;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.TypePayment;

public class VoucherPayment implements PaymentMethod {

	private final VoucherPaymentService voucherPaymentService;

	public VoucherPayment(VoucherPaymentService voucherPaymentService) {
		this.voucherPaymentService = voucherPaymentService;
	}

	@Override
	public PaymentExecutionResult payment(BigDecimal totalAmount, PaymentDetails dto) {
		
		boolean applied = voucherPaymentService.voucherPayment(dto.voucherId(), totalAmount);
		
		if (!applied) {
			return new PaymentExecutionResult(false, null, "Insufficient voucher balance.");
		}
		
		return new PaymentExecutionResult(true, "Voucher", null);
	}

	@Override
	public TypePayment getType() {
		return TypePayment.VOUCHER;
	}
}