package com.cleancode.ecommerce.replacement.application.usecase;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.replacement.application.dto.CreateReplacementDto;
import com.cleancode.ecommerce.replacement.application.usecase.contract.CreateReplacement;
import com.cleancode.ecommerce.replacement.domain.Explain;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.Quantity;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class CreateReplacementImp implements CreateReplacement {

	private final ReplacementRepository repository;
	
	public CreateReplacementImp(ReplacementRepository repository) {
		this.repository = repository;
	}
	
	public void execute (CreateReplacementDto dto) {
		
		Replacement replacement = new Replacement(
			    new ReservationId(dto.getReservationId()),
			    dto.getReason(),
			    new Explain(dto.getExplain()),
			    new CustomerId(dto.getCustomerId()),
			    new Quantity(dto.getQuantity())
		);
		
		repository.save(replacement);
	}
}