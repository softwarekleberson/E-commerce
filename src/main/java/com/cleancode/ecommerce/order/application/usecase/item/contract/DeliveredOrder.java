package com.cleancode.ecommerce.order.application.usecase.item.contract;

public interface DeliveredOrder {

	public void execute (String orderId, String reservationId);
}
