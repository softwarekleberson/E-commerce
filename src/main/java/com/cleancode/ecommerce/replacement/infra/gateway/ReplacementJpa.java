package com.cleancode.ecommerce.replacement.infra.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.replacement.infra.persistece.ReplacementEntity;

public interface ReplacementJpa extends JpaRepository<ReplacementEntity, String>{

}
