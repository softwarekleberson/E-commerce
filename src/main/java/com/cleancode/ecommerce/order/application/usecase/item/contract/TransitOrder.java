package com.cleancode.ecommerce.order.application.usecase.item.contract;

public interface TransitOrder {

	public void execute (String orderId, String reservationId);
}
