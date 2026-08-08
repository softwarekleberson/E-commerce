package com.cleancode.ecommerce.customer.application.usecase.contract;

public interface DeleteCharge {

	public void execute (String email, String ChargeId);
}
