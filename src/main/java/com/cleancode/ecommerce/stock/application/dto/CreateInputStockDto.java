package com.cleancode.ecommerce.stock.application.dto;

import java.math.BigDecimal;

import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.stock.domain.ProductQuality;

public class CreateInputStockDto {

	private String productId;
	private int quantity;
	private ProductQuality productQuality;
	private BigDecimal purchasePrice;
	private TypeCoin coin;
	private String supplier;
	
	public CreateInputStockDto(int quantity, ProductQuality productQuality, BigDecimal purchasePrice, TypeCoin coin ,String supplier) {
		this.quantity = quantity;
		this.productQuality = productQuality;
		this.purchasePrice = purchasePrice;
		this.coin = coin;
		this.supplier = supplier;
	}
	
	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public ProductQuality getProductQuality() {
		return productQuality;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	
	public TypeCoin getCoin() {
		return coin;
	}

	public String getSupplier() {
		return supplier;
	}
}