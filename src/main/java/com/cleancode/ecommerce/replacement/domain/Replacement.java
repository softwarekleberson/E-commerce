package com.cleancode.ecommerce.replacement.domain;

import java.util.Objects;

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
	
	public Replacement(String id, String reservationId, Reason reason, String explain, Status status) {
		this.id = new Id(id);
		this.reservationId = new ReservationId(reservationId);
		this.reason = reason;
		this.explain = new Explain(explain);
		this.status = status;
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
}
