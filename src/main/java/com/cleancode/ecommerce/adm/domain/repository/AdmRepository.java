package com.cleancode.ecommerce.adm.domain.repository;

import java.util.Optional;

import com.cleancode.ecommerce.adm.domain.Adm;

public interface AdmRepository {

	void save (Adm adm);
	Optional<Adm> findByEmail (String email);
}
