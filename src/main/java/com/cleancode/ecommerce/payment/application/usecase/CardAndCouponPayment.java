package com.cleancode.ecommerce.payment.application.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentGatewayClient;
import com.cleancode.ecommerce.payment.application.service.voucher.contract.VoucherPaymentService;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.TypePayment;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;

public class CardAndCouponPayment implements PaymentMethod {

	public static final BigDecimal MINIMUM_CARD_PURCHASE_AMOUNT = new BigDecimal("10.00");		

	private final PaymentGatewayClient gateway;
	private final VoucherPaymentService service;

	public CardAndCouponPayment(VoucherPaymentService service, PaymentGatewayClient gateway) {
		this.service = service;
		this.gateway = gateway;
	}
	
	@Override
	public PaymentExecutionResult payment(BigDecimal totalAmount, PaymentDetails dto) {
		
		validateDto(dto);
		
		BigDecimal valueVoucher = service.valueVoucher(dto.voucherId());
		
		BigDecimal amountToBePaidByCard = totalAmount.subtract(valueVoucher);
		
		if (amountToBePaidByCard.compareTo(BigDecimal.ZERO) <= 0) {
			return new PaymentExecutionResult(false, null, "Voucher covers the full amount. Use Voucher-only payment method.");
		}
		
		if (amountToBePaidByCard.compareTo(MINIMUM_CARD_PURCHASE_AMOUNT) < 0) {
			return new PaymentExecutionResult(false, null, "The amount to be paid by card must be at least " + MINIMUM_CARD_PURCHASE_AMOUNT);
		}
		
		var cardResponse = gateway.authorize(dto.numberCardOne(), amountToBePaidByCard);
		
		if (!cardResponse.isApproved()) {
			return new PaymentExecutionResult(false, null, cardResponse.declineReason());
		}
		
		boolean voucherRedeemed = service.voucherPayment(dto.voucherId(), valueVoucher);
		
		if (!voucherRedeemed) {
			gateway.refund(dto.numberCardOne(), amountToBePaidByCard); // 💡 Compensating Transaction (Garantia Atômica)
			return new PaymentExecutionResult(false, null, "Failed to apply voucher. Card payment was refunded.");
		}
		
		return new PaymentExecutionResult(true, "Card And Voucher", null);
	}

	private void validateDto(PaymentDetails dto) {
		if (dto == null) {
			throw new IllegalDomainPayment("Payment details must be provided");
		}
		
		if (dto.numberCardOne() == null || dto.numberCardOne().isBlank()) {
			throw new IllegalDomainPayment("Card number must be provided **");
		}
	}

	@Override
	public TypePayment getType() {
		return TypePayment.VOUCHER_CARD;
	}
}