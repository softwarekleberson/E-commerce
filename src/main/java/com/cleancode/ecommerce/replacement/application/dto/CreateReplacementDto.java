package com.cleancode.ecommerce.replacement.application.dto;

import com.cleancode.ecommerce.replacement.domain.Reason;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateReplacementDto {

	@NotBlank(message = "The Reservation Id need present")
	private String reservationId;
	
	@NotNull(message = "CHANGE OF MIND, DEFECT, DIFFERENT ITEM, MISSING ITEM")
	private Reason reason;
	
	@NotBlank(message = "You need inform the reason")
	private String explain;
	
	public CreateReplacementDto() {}
	
	public CreateReplacementDto(String reservationId, Reason reason, String explain) {
		this.reservationId = reservationId;
		this.reason = reason;
		this.explain = explain;
	}

	public String getReservationId() {
		return reservationId;
	}

	public Reason getReason() {
		return reason;
	}

	public String getExplain() {
		return explain;
	}
}
