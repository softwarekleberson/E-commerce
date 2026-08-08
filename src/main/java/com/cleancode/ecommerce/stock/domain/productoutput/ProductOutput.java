package com.cleancode.ecommerce.stock.domain.productoutput;

import java.util.Objects;

import com.cleancode.ecommerce.order.domain.OrderId;
import com.cleancode.ecommerce.product.domain.ProductId;

public class ProductOutput {

	private final OrderId orderId;
	private final ProductId productId;
	private final int quantity;

	public ProductOutput(ProductId productId, int quantity) {
		this.orderId = new OrderId();
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public ProductOutput (String orderId, String productId, int quantity) {
		this.orderId = new OrderId(orderId);
		this.productId = new ProductId(productId);
		this.quantity = quantity;
	}

	public OrderId getOrderId() {
		return orderId;
	}
	
	public ProductId getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductOutput other = (ProductOutput) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(productId, other.productId)
				&& quantity == other.quantity;
	}
}