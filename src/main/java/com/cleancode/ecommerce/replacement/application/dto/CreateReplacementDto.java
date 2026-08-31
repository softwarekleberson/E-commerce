package com.cleancode.ecommerce.replacement.application.dto;

import com.cleancode.ecommerce.replacement.domain.Reason;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateReplacementDto {

	@NotBlank(message = "The Reservation Id need present")
	private String reservationId;
	
	@NotNull(message = "CHANGE OF MIND, DEFECT, DIFFERENT ITEM, MISSING ITEM")
	private Reason reason;
	
	@NotBlank(message = "You need inform the reason")
	private String explain;
	
	@NotBlank
	private String customerId;
	
	@NotNull
	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;
	
	public CreateReplacementDto() {}
	
	public CreateReplacementDto(String reservationId, Reason reason, String explain, String customerId, int quantity) {
		this.reservationId = reservationId;
		this.reason = reason;
		this.explain = explain;
		this.customerId = customerId;
		this.quantity = quantity;
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
	
	public String getCustomerId() {
		return customerId;
	}
	
	public int getQuantity() {
		return quantity;
	}
}
