package com.cleancode.ecommerce.customer.application.usecase.contract;

public interface DeleteDelivery {

	public void execute(String email, String deliveryId);
}
