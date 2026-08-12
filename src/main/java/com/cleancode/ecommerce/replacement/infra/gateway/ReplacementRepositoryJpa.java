package com.cleancode.ecommerce.replacement.infra.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;

@Repository
public class ReplacementRepositoryJpa implements ReplacementRepository{

	private final ReplacementJpa jpa;
	
	public ReplacementRepositoryJpa(ReplacementJpa jpa) {
		this.jpa = jpa;
	}

	@Transactional
	@Override
	public void save(Replacement replacement) {
		// TODO Auto-generated method stub
	}

	@Override
	public Page<Replacement> getReplacementOpen(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}