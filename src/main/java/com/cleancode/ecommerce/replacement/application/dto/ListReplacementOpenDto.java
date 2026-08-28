package com.cleancode.ecommerce.replacement.application.dto;

import com.cleancode.ecommerce.replacement.domain.Reason;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.Status;

public record ListReplacementOpenDto(
	
	String id, String reservationId, Reason reason,
	int quantity, String explain, Status status
		
		)
{
	public ListReplacementOpenDto(Replacement rep) {
		this(rep.getId().getId(),
			 rep.getReservationId().getReservationId(),
			 rep.getReason(),
			 rep.getQuantity().getQuantity(),
			 rep.getExplain().getExplain(),
			 rep.getStatus()
		);
	}
}
