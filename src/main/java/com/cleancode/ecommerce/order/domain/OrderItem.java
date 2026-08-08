package com.cleancode.ecommerce.order.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;

public final class OrderItem {

	private final String productId;
	private final BigDecimal price;
	private final int quantity;
	private final BigDecimal subtotal;
	private final String reservationId;
	
	public OrderItem(String productId, BigDecimal price, int quantity, String reservationId) {
		if(reservationId == null) throw new IllegalDomainPayment("Stock out id is required");
		if(productId == null) throw new IllegalDomainPayment("Name not be null");
		if (price == null || price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalDomainPayment("Invalid Price it be greater than 0");
        if (quantity <= 0) throw new IllegalDomainPayment("The quantity requires that it be greater than 0.");
		
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
		this.reservationId = reservationId;
		this.subtotal = subTotal();
	}
		
	private BigDecimal subTotal() {
		return price.multiply(BigDecimal.valueOf(quantity));
	}
	
	public String getProductId() {
		return productId;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	
	public String getStockOutId() {
		return reservationId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, price, quantity, reservationId, subtotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		return Objects.equals(productId, other.productId) && Objects.equals(price, other.price) && quantity == other.quantity
				&& Objects.equals(reservationId, other.reservationId) && Objects.equals(subtotal, other.subtotal);
	}
}