package com.cleancode.ecommerce.stock.infra.gateway;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;
import com.cleancode.ecommerce.stock.infra.mapper.StockMapper;
import com.cleancode.ecommerce.stock.infra.persistence.StockEntity;

@Repository
public class StockRepositoryJpa implements StockRepository {

	private final StockJpa jpa;

	public StockRepositoryJpa(StockJpa jpa) {
		this.jpa = jpa;
	}

	@Transactional
	@Override
	public Stock save(Stock stoke) {
		StockEntity entity = StockMapper.toEntity(stoke);
		jpa.save(entity);
		Stock domain = StockMapper.toDomain(entity);
		return domain;
	}

	@Transactional
	@Override
	public Optional <Stock> getStock(String id) {
		return jpa.findByProductId(id).map(StockMapper::toDomain);
	}
	
	@Transactional
	@Override
	public Optional <Stock> findByStock(String id) {
		return jpa.findById(id).map(StockMapper::toDomain);
	}

	@Transactional
	@Override
	public Optional <Stock> findStockByReservationId(String reservationId) {
	    return jpa.findStockByReservationId(reservationId)
	              .map(StockMapper::toDomain);
	}
}