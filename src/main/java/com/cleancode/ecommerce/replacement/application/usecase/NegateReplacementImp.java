package com.cleancode.ecommerce.replacement.application.usecase;

import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;

public class NegateReplacementImp implements NegateReplacement{

	private final ReplacementRepository repository;

	public NegateReplacementImp(ReplacementRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void execute (String reservationId) {
		Replacement replacement = repository.getReplacementById(reservationId)
				.orElseThrow(() -> new IllegalReplacementException("Replacement not found by id : " + reservationId));
		
		var replacementNegate = replacement.negate();
		repository.save(replacementNegate);
	}
}