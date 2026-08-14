package com.cleancode.ecommerce.replacement.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.replacement.application.dto.ListReplacementOpenDto;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;

public class ListReplacementImpl implements ListReplacement{

	private final ReplacementRepository repository;

	public ListReplacementImpl(ReplacementRepository repository) {
		this.repository = repository;
	}

	@Override
	public Page<ListReplacementOpenDto> execute (Pageable pageable) {
		Page<Replacement> replacement = repository.getReplacementOpen(pageable);
		
		return replacement.map(ListReplacementOpenDto::new);
	}
}
