package com.cleancode.ecommerce.replacement.application.usecase;

import com.cleancode.ecommerce.replacement.application.dto.CreateReplacementDto;
import com.cleancode.ecommerce.replacement.domain.Explain;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class CreateReplacementImp implements CreateReplacement {

	private final ReplacementRepository repository;
	
	public CreateReplacementImp(ReplacementRepository repository) {
		this.repository = repository;
	}
	
	public void execute (CreateReplacementDto dto) {
		
		Replacement replacement = 
		new Replacement(new ReservationId(dto.getReservationId()),
						dto.getReason(),
						new Explain(dto.getExplain())
		);
		
		repository.save(replacement);
	}
}