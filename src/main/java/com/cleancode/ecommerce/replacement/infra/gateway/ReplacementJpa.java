package com.cleancode.ecommerce.replacement.infra.gateway;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.replacement.infra.persistece.ReplacementEntity;
import com.cleancode.ecommerce.replacement.infra.persistece.StatusEntity;

public interface ReplacementJpa extends JpaRepository<ReplacementEntity, String>{

	Optional<ReplacementEntity> findByReservationId(String reservationId);

	Page<ReplacementEntity> findByStatus(StatusEntity open, Pageable pageable);

}
