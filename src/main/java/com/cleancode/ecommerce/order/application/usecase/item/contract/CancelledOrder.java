package com.cleancode.ecommerce.order.application.usecase.item.contract;

public interface CancelledOrder {

	public void execute (String orderId, String reservationId);
}
