package com.cleancode.ecommerce.product.application.usecase.contract;

public interface ActiveProduct {

	void execute(String productId, String stockId);

}
