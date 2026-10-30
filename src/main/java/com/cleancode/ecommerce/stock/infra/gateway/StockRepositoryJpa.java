package com.cleancode.ecommerce.stock.infra.gateway;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public List<Stock> saveAll(Collection<Stock> stocks) {
		return jpa.saveAll(stocks.stream().map(StockMapper::toEntity).toList()).stream()
				.map(StockMapper::toDomain)
				.toList();
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

	@Transactional
	@Override
	public List<Stock> findStocksByReservationIds(Collection<String> reservationIds) {
		if (reservationIds.isEmpty()) {
			return List.of();
		}

		List<String> stockIds = jpa.findStockIdsByReservationIds(reservationIds).stream().sorted().toList();
		if (stockIds.isEmpty()) {
			return List.of();
		}

		List<StockEntity> entities = jpa.lockStocksByIds(stockIds);
		List<String> lockedStockIds = entities.stream().map(StockEntity::getStock_id).toList();
		if (lockedStockIds.isEmpty()) {
			return List.of();
		}
		jpa.fetchReservationsByStockIds(lockedStockIds);
		jpa.fetchInputsByStockIds(lockedStockIds);
		jpa.fetchOutputsByStockIds(lockedStockIds);

		return entities.stream().map(StockMapper::toDomain).collect(Collectors.toList());
	}
}
