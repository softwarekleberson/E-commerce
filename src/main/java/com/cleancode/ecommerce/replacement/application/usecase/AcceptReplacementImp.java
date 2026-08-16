package com.cleancode.ecommerce.replacement.application.usecase;

import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;

public class AcceptReplacementImp implements AcceptReplacement{

	private final ReplacementRepository repository;

	public AcceptReplacementImp(ReplacementRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void execute (String reservationId) {
		Replacement replacement = repository.getReplacementById(reservationId)
				.orElseThrow(() -> new IllegalReplacementException("Replacement not found by id : " + reservationId));
		
		var replacementAccept = replacement.accept();
		repository.save(replacementAccept);
	}
}