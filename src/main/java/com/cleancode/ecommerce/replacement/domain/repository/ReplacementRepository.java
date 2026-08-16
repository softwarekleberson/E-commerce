package com.cleancode.ecommerce.replacement.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.replacement.domain.Replacement;

public interface ReplacementRepository {

	void save(Replacement replacement);
	Optional<Replacement> getReplacementById (String reservationId);
	Page<Replacement> getReplacementOpen(Pageable pageable);
}
