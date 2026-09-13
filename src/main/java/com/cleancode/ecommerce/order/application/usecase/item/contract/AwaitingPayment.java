package com.cleancode.ecommerce.order.application.usecase.item.contract;

public interface AwaitingPayment {

	public void execute (String orderId, String reservationId);
}
