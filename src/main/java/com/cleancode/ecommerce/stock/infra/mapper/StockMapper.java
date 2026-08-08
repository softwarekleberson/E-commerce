
package com.cleancode.ecommerce.stock.infra.mapper;

import java.util.stream.Collectors;

import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.StockId;
import com.cleancode.ecommerce.stock.infra.persistence.StockEntity;

public class StockMapper {

	public static Stock toDomain(StockEntity entity) {
		if (entity == null)
			return null;

		Stock stock = new Stock(new StockId(entity.getStock_id()), new ProductId(entity.getProductId()),
				entity.getTotal_quantity());

		if (entity.getReservations() != null && !entity.getReservations().isEmpty()) {
			stock.addReservations(
					entity.getReservations().stream().map(ReservationMapper::toDomain).collect(Collectors.toList()));
		}

		if (entity.getInputs() != null && !entity.getInputs().isEmpty()) {
			stock.addProductInput(
					entity.getInputs().stream().map(StockProductInputMapper::toDomain).collect(Collectors.toList()));
		}

		if (entity.getOutputs() != null && !entity.getOutputs().isEmpty()) {
			stock.addProductOutput(
					entity.getOutputs().stream().map(StockProductOutputMapper::toDomain).collect(Collectors.toList()));
		}

		return stock;
	}

	public static StockEntity toEntity(Stock stock) {
		if (stock == null)
			return null;

		StockEntity entity = new StockEntity();
		entity.setStock_id(stock.getStockId().getStockId());
		entity.setProductId(stock.getProductId().getProductId());
		entity.setTotal_quantity(stock.getTotalQuantity());
		entity.setQuantity_available(stock.getQuantityAvailable());

		if (stock.getReservations() != null && !stock.getReservations().isEmpty()) {
			entity.setReservations(stock.getReservations().stream().map(r -> ReservationMapper.toEntity(r, entity))
					.collect(Collectors.toList()));
		}

		if (stock.getProductInput() != null && !stock.getProductInput().isEmpty()) {
			entity.setInputs(stock.getProductInput().stream().map(i -> StockProductInputMapper.toEntity(i, entity))
					.collect(Collectors.toList()));
		}

		if (stock.getProductOutput() != null && !stock.getProductOutput().isEmpty()) {
			entity.setOutputs(stock.getProductOutput().stream().map(o -> StockProductOutputMapper.toEntity(o, entity))
					.collect(Collectors.toList()));
		}

		return entity;
	}
}
