package com.cleancode.ecommerce.cart.application.dtos.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateCartDto {

	private String email;
	
	@NotBlank(message = "Product id not found")
	private String productId;
	
	@Min(1)
	private int quantity;
	
	public CreateCartDto(String productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}
}