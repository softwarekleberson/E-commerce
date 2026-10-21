package com.cleancode.ecommerce.payment.application.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.application.service.payment.contract.PaymentGatewayClient;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.TypePayment;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;

public class TwoCardsPayment implements PaymentMethod {

    public static final BigDecimal MINIMUM_CARD_PURCHASE_AMOUNT =
            new BigDecimal("10.00");

    private final PaymentGatewayClient gateway;

    public TwoCardsPayment(PaymentGatewayClient gateway) {
        this.gateway = gateway;
    }

    @Override
    public PaymentExecutionResult payment(BigDecimal totalAmount, PaymentDetails dto) {
        validateDto(dto);

        BigDecimal sumOfTheTwoCards = dto.amountCardOne().add(dto.amountCardTwo());

        if (sumOfTheTwoCards.compareTo(totalAmount) != 0) {
            throw new IllegalDomainPayment(
                    "The payment amount on both cards must match the order amount."
            );
        }

        var responseFirstCard = gateway.authorize(
                dto.numberCardOne(),
                dto.amountCardOne()
        );

        if (!responseFirstCard.isApproved()) {
            return new PaymentExecutionResult(
                    false,
                    null,
                    responseFirstCard.declineReason()
            );
        }

        var responseSecondCard = gateway.authorize(
                dto.numberCardTwo(),
                dto.amountCardTwo()
        );

        if (!responseSecondCard.isApproved()) {
            gateway.refund(dto.numberCardOne(), dto.amountCardOne());

            return new PaymentExecutionResult(
                    false,
                    null,
                    responseSecondCard.declineReason()
            );
        }

        return new PaymentExecutionResult(true, "Payment Two Cards", null);
    }

    private void validateDto(PaymentDetails dto) {
        if (dto == null) {
            throw new IllegalDomainPayment("Payment details must be provided.");
        }

        if (dto.numberCardOne() == null || dto.numberCardOne().isBlank()) {
            throw new IllegalDomainPayment("Card number must be provided.");
        }

        if (dto.amountCardOne() == null
                || dto.amountCardOne().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalDomainPayment(
                    "The payment amount on the first card must be greater than 0."
            );
        }

        if (dto.amountCardOne().compareTo(MINIMUM_CARD_PURCHASE_AMOUNT) < 0) {
            throw new IllegalDomainPayment(
                    "The payment amount on the first card must be at least "
                            + MINIMUM_CARD_PURCHASE_AMOUNT
            );
        }

        if (dto.numberCardTwo() == null || dto.numberCardTwo().isBlank()) {
            throw new IllegalDomainPayment("The second card number must be provided.");
        }

        if (dto.amountCardTwo() == null
                || dto.amountCardTwo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalDomainPayment(
                    "The payment amount on the second card must be greater than 0."
            );
        }

        if (dto.amountCardTwo().compareTo(MINIMUM_CARD_PURCHASE_AMOUNT) < 0) {
            throw new IllegalDomainPayment(
                    "The payment amount on the second card must be at least "
                            + MINIMUM_CARD_PURCHASE_AMOUNT
            );
        }

        if (dto.numberCardOne().trim().equalsIgnoreCase(dto.numberCardTwo().trim())) {
            throw new IllegalDomainPayment("The numbers on both cards match.");
        }
    }

    @Override
    public TypePayment getType() {
        return TypePayment.TWO_CARDS;
    }
}
