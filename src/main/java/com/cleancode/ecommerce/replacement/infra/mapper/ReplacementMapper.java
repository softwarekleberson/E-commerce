package com.cleancode.ecommerce.replacement.infra.mapper;

import com.cleancode.ecommerce.replacement.domain.Explain;
import com.cleancode.ecommerce.replacement.domain.Id;
import com.cleancode.ecommerce.replacement.domain.Reason;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.Status;
import com.cleancode.ecommerce.replacement.infra.persistece.ReasonEntity;
import com.cleancode.ecommerce.replacement.infra.persistece.ReplacementEntity;
import com.cleancode.ecommerce.replacement.infra.persistece.StatusEntity;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class ReplacementMapper {

	private ReplacementMapper() {
	}

	public static Replacement toDomain(ReplacementEntity entity) {
		if (entity == null) {
			return null;
		}

		return new Replacement(
			new Id(entity.getId()),
			new ReservationId(entity.getReservationId()),
			Reason.valueOf(entity.getReason().name()),
			new Explain(entity.getExplain()),
			Status.valueOf(entity.getStatus().name())
		);
	}

	public static ReplacementEntity toEntity(Replacement domain) {
		if (domain == null) {
			return null;
		}

		ReplacementEntity entity = new ReplacementEntity();
		return updateEntityFromDomain(domain, entity);
	}

	public static ReplacementEntity toEntity(Replacement domain, ReplacementEntity entity) {
		if (domain == null || entity == null) {
			return entity;
		}
		return updateEntityFromDomain(domain, entity);
	}

	public static ReplacementEntity updateEntityFromDomain(Replacement domain, ReplacementEntity entity) {
		System.out.println(domain + "Mapper");
		entity.setId(domain.getId().getId());
		entity.setReservationId(domain.getReservationId().getReservationId());
		entity.setReason(ReasonEntity.valueOf(domain.getReason().name()));
		entity.setExplain(domain.getExplain().getExplain());
		entity.setStatus(StatusEntity.valueOf(domain.getStatus().name()));
		return entity;
	}
}