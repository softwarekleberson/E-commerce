package com.cleancode.ecommerce.order.application.usecase.item.contract;

public interface OrderStatusAfterInitiatingAnExchangeRequest {

	public void execute(String orderId, String reservationId);
}
