package com.cleancode.ecommerce.order.application.usecase.item.contract;

public interface OrderStatusAfterReview {

	public void execute(String orderId, String reservationId, boolean flag);
}
