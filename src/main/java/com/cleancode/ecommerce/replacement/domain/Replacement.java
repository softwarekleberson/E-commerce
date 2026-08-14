package com.cleancode.ecommerce.replacement.domain;

import java.util.Objects;

import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class Replacement {

	private final Id id;
	private final ReservationId reservationId;
	private final Reason reason;
	private final Explain explain;
	private Status status;
	
	public Replacement(ReservationId reservationId, Reason reason, Explain explain) {
		this.id = new Id();
		this.reservationId = reservationId;
		this.reason = reason;
		this.explain = explain;
		this.status = Status.OPEN;
	}
	
	public Replacement(Id id, ReservationId reservationId, Reason reason, Explain explain, Status status) {
	    this.id = id;
	    this.reservationId = reservationId;
	    this.reason = reason;
	    this.explain = explain;
	    this.status = status;
	}
	
	public Replacement accept() {
		ensureIsOpen();
		return new Replacement(this.id, this.reservationId, this.reason, this.explain, Status.ACCEPTS);	
	}
	
	public Replacement negate() {
		ensureIsOpen();
		return new Replacement(this.id, this.reservationId, this.reason, this.explain, Status.NEGATED);
	}
	
	public Replacement cancel() {
		ensureIsOpen();
		return new Replacement(this.id, this.reservationId, this.reason, this.explain, Status.CLOSE);
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
