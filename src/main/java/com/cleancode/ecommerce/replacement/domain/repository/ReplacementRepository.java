package com.cleancode.ecommerce.replacement.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.replacement.domain.Replacement;

public interface ReplacementRepository {

	void save(Replacement replacement);
	Page<Replacement> getReplacementOpen(Pageable pageable);
}
