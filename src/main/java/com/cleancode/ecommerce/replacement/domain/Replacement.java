package com.cleancode.ecommerce.replacement.domain;

import java.math.BigDecimal;
import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class Replacement {

	private final Id id;
	private final ReservationId reservationId;
	private final Reason reason;
	private final Explain explain;
	private Status status;
	private final CustomerId customerId;
	private final Quantity quantity;
	
	public Replacement(ReservationId reservationId, Reason reason, Explain explain, CustomerId customerId, Quantity quantity) {
		this.id = new Id();
		this.reservationId = reservationId;
		this.reason = reason;
		this.explain = explain;
		this.status = Status.OPEN;
		this.customerId = customerId;
		this.quantity = quantity;
	}
	
	public Replacement(Id id, ReservationId reservationId, Reason reason, Explain explain, Status status, CustomerId customerId, Quantity quantity) {
	    this.id = id;
	    this.reservationId = reservationId;
	    this.reason = reason;
	    this.explain = explain;
	    this.status = status;
	    this.customerId = customerId;
	    this.quantity = quantity;
	}
	
	public BigDecimal exchangeVoucherValue(BigDecimal subtotal) {
		System.out.println(this.quantity + "Quantidade");
		return subtotal.multiply(BigDecimal.valueOf(this.quantity.getQuantity()));
	}
	
	public Replacement accept() {
		ensureIsOpen();
		return new Replacement(this.id, this.reservationId, this.reason, this.explain, Status.ACCEPTS, this.customerId, this.quantity);	
	}
	
	public Replacement negate() {
		ensureIsOpen();
		return new Replacement(this.id, this.reservationId, this.reason, this.explain, Status.NEGATED, this.customerId, this.quantity);
	}
	
	public Replacement cancel() {
		ensureIsOpen();
		return new Replacement(this.id, this.reservationId, this.reason, this.explain, Status.CLOSE, this.customerId, this.quantity);
	}
	
	private void ensureIsOpen() {
		if(this.status != Status.OPEN) {
			throw new IllegalReplacementException("Only requests with the status OPEN can be modified.");
		}
	}

	public Id getId() {
		return id;
	}

	public ReservationId getReservationId() {
		return reservationId;
	}

	public Reason getReason() {
		return reason;
	}

	public Explain getExplain() {
		return explain;
	}

	public Status getStatus() {
		return status;
	}
	
	public CustomerId getCustomerId() {
		return customerId;
	}
	
	public Quantity getQuantity() {
		return quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Replacement other = (Replacement) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Replacement [id=" + id + ", reservationId=" + reservationId + ", reason=" + reason + ", explain="
				+ explain + ", status=" + status + "]";
	}
	
}
