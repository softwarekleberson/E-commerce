package com.cleancode.ecommerce.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.order.domain.state.OrderState;
import com.cleancode.ecommerce.order.domain.state.PendingState;
import com.cleancode.ecommerce.payment.domain.Total;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public final class Order {

	private final OrderId orderId;
	private final CustomerId customerId;
	private final String deliveryId;
	private final LocalDateTime createdAt;

	private List<OrderItem> items = new ArrayList<>();
	private Total total;
	private OrderState orderState;
	private OrderStatus orderStatus;

	public Order(String customerId, String deliveryId) {
		this.orderId = new OrderId();
		this.customerId = new CustomerId(customerId);
		this.deliveryId = deliveryId;
		this.createdAt = LocalDateTime.now();
		this.orderState = new PendingState();
		this.orderStatus = OrderStatus.PENDING;
	}

	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
		this.orderStatus = orderState.getOrderStatus();
	}

	public void pay() {
		if (this.total == null || this.total.getTotalValue().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalDomainPayment("Order cannot be paid with zero total.");
		}

		orderState.pay(this);
	}

	public void cancel() {
		orderState.cancel(this);
	}

	public void ship() {
		orderState.ship(this);
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void addItem(String name, BigDecimal price, int quantity, String reservationId) {
		OrderItem item = new OrderItem(name, price, quantity, reservationId);
		this.items.add(item);
		calculateTotal();
	}

	public Total calculateTotal() {
		BigDecimal totalValue = items.stream().map(OrderItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

		this.total = new Total(totalValue, TypeCoin.DOLAR);
		return this.total;
	}

	public OrderId getOrderId() {
		return orderId;
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public List<OrderItem> getItems() {
		return Collections.unmodifiableList(items);
	}

	public Total getTotal() {
		return total;
	}

	public OrderState getOrderState() {
		return orderState;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void addOrderItem(List<OrderItem> collect) {
		// TODO Auto-generated method stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(orderId, other.orderId);
	}
}
