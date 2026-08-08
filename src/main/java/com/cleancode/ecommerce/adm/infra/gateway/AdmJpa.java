package com.cleancode.ecommerce.adm.infra.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.adm.infra.persistence.AdmEntity;

public interface AdmJpa extends JpaRepository<AdmEntity, String> {

	Optional<AdmEntity> findByEmail(String email);
}
