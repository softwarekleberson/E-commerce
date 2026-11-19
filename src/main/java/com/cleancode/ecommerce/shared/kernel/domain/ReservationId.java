package com.cleancode.ecommerce.stock.domain.reservation;

import java.util.Objects;
import java.util.UUID;

public class ReservationId {

	private final String reservationId;

    public ReservationId() { 
    	this.reservationId = UUID.randomUUID().toString();
    }
    
    public ReservationId(String id) {
    	this.reservationId = id;
    }

    public String getReservationId() { return reservationId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationId)) return false;
        ReservationId other = (ReservationId) o;
        return Objects.equals(this.reservationId, other.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }
}
