package com.cleancode.ecommerce.stock.infra.mapper;

import com.cleancode.ecommerce.stock.infra.persistence.StockOutputEntity;
import com.cleancode.ecommerce.stock.domain.productoutput.ProductOutput;
import com.cleancode.ecommerce.stock.infra.persistence.StockEntity;

public class StockProductOutputMapper {

	public static ProductOutput toDomain(StockOutputEntity entity) {
		return new ProductOutput(entity.getOrder_id(), entity.getProduct_id(), entity.getQuantity());
	}

	public static StockOutputEntity toEntity(ProductOutput domain, StockEntity stockEntity) {
		StockOutputEntity entity = new StockOutputEntity();
		entity.setOrder_id(domain.getOrderId().getOrderId());
		entity.setProduct_id(domain.getProductId().getProductId());
		entity.setQuantity(domain.getQuantity());
		entity.setStock(stockEntity);
		return entity;
	}
}