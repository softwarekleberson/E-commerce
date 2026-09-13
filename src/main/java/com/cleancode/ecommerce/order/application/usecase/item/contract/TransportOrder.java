package com.cleancode.ecommerce.order.application.usecase.item.contract;

public interface TransportOrder {

	public void execute (String orderId, String reservationId);
}
