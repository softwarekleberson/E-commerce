package com.cleancode.ecommerce.stock.infra.mapper;

import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.stock.domain.ProductQuality;
import com.cleancode.ecommerce.stock.domain.productinput.ProductInput;
import com.cleancode.ecommerce.stock.infra.persistence.StockInputEntity;
import com.cleancode.ecommerce.stock.infra.persistence.ProductQualityEntity;
import com.cleancode.ecommerce.stock.infra.persistence.StockEntity;

public class StockProductInputMapper {

	public static ProductInput toDomain(StockInputEntity entity) {
		return new ProductInput(entity.getQuantity(), ProductQuality.valueOf(entity.getProduct_quality().name()),
			   new Price(entity.getPurchase_price(), entity.getCoin()), entity.getSupplier());
	}

	public static StockInputEntity toEntity(ProductInput domain, StockEntity stockEntity) {
		StockInputEntity entity = new StockInputEntity();
		entity.setQuantity(domain.getQuantity().getQuantity());
		entity.setProduct_quality(ProductQualityEntity.valueOf(domain.getProductQuality().name()));
		entity.setPurchase_price(domain.getPurchasePrice().getPrice());
		entity.setCoin(domain.getPurchasePrice().getCoin());
		entity.setSupplier(domain.getSupplier().getSupplier());
		entity.setStock(stockEntity);
		return entity;
	}
}