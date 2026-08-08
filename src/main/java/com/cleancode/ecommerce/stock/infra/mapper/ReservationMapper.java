package com.cleancode.ecommerce.stock.infra.mapper;

import com.cleancode.ecommerce.stock.domain.reservation.Reservations;
import com.cleancode.ecommerce.stock.domain.reservation.ReserveStatus;
import com.cleancode.ecommerce.stock.infra.persistence.ReservationEntity;
import com.cleancode.ecommerce.stock.infra.persistence.ReserveStatusEntity;
import com.cleancode.ecommerce.stock.infra.persistence.StockEntity;

public class ReservationMapper {

	public static Reservations toDomain (ReservationEntity entity) {
		return new Reservations(entity.getReservation_id(), entity.getCart_id(), entity.getCustomer_id(), entity.getQuantity(), entity.getReservation_time(), ReserveStatus.valueOf(entity.getReserve_status().name()));
	}
	
	public static ReservationEntity toEntity (Reservations domain, StockEntity stockEntity) {
		ReservationEntity entity = new ReservationEntity();
		entity.setReservation_id(domain.getReservationId());
		entity.setCart_id(domain.getCartId().getCartId());
		entity.setCustomer_id(domain.getCustomerId().getValue());
		entity.setQuantity(domain.getQuantity());
		entity.setReservation_time(domain.getReservationTime());
		entity.setReserve_status(ReserveStatusEntity.valueOf(domain.getReserveStatus().name()));
		entity.setStock(stockEntity);
		return entity;
	}
}