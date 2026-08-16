package com.cleancode.ecommerce.replacement.infra.gateway;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;
import com.cleancode.ecommerce.replacement.infra.mapper.ReplacementMapper;
import com.cleancode.ecommerce.replacement.infra.persistece.ReplacementEntity;
import com.cleancode.ecommerce.replacement.infra.persistece.StatusEntity;

@Repository
public class ReplacementRepositoryJpa implements ReplacementRepository {

	private final ReplacementJpa jpa;

	public ReplacementRepositoryJpa(ReplacementJpa jpa) {
		this.jpa = jpa;
	}

	@Transactional
	@Override
	public void save(Replacement replacement) {
	
		Optional<ReplacementEntity> optionEntity = jpa.findByReservationId(replacement.getReservationId().getReservationId());
		
		ReplacementEntity entity = optionEntity
				.map(existingEntity -> ReplacementMapper.toEntity(replacement, existingEntity))
				.orElseGet(() -> ReplacementMapper.toEntity(replacement));

		jpa.save(entity);
	}

	@Override
	public Page<Replacement> getReplacementOpen(Pageable pageable) {
		return jpa.findByStatus(StatusEntity.OPEN, pageable)
			   .map(ReplacementMapper::toDomain);
		
	}

	@Override
	public Optional<Replacement> getReplacementById(String reservationId) {
		return jpa.findByReservationId(reservationId).map(ReplacementMapper::toDomain);
	}
}