package com.cleancode.ecommerce.product.infra.mapper.toy;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.product.domain.toy.Toy;
import com.cleancode.ecommerce.product.infra.mapper.MidiaInputMapper;
import com.cleancode.ecommerce.product.infra.mapper.ProductSubtypeMapper;
import com.cleancode.ecommerce.product.infra.persistence.product.MidiaEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductCategoryEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductStatusCategoryEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.TypeCoinEntity;
import com.cleancode.ecommerce.product.infra.persistence.toy.ToyEntity;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

@Component
public class ToyMapper implements ProductSubtypeMapper<Toy, ToyEntity> {

	@Override
	public Class<Toy> domainType() { return Toy.class; }

	@Override
	public Class<ToyEntity> entityType() { return ToyEntity.class; }

	@Override
	public Toy toDomain(ToyEntity entity) {
		return new Toy(new ProductId(entity.getProduct_id()), entity.isActive(), new Name(entity.getName()),
				new Description(entity.getDescription()),
				new Price(entity.getPrice(), TypeCoin.valueOf(entity.getType_coin().name())),
				new Brand(entity.getBrand()), MidiaInputMapper.toMidiaList(entity.getMidias()),
				new Pricing(entity.getPricing()), entity.getToyType(), entity.getAgeRange(), entity.getMaterial(),
				entity.getNumberOfPieces(), entity.isBatteryRequired(), entity.isBatteryIncluded());
	}

	@Override
	public ToyEntity toEntity(Toy domain) {
		ToyEntity entity = new ToyEntity();
		entity.setProduct_id(domain.getProductId().getProductId());
		entity.setActive(domain.isActive());
		entity.setName(domain.getName().getName());
		entity.setDescription(domain.getDescription().getDescription());
		entity.setPrice(domain.getPrice().getPrice());
		entity.setType_coin(TypeCoinEntity.valueOf(domain.getPrice().getCoin().name()));
		entity.setCategory(ProductCategoryEntity.TOYS);
		entity.setBrand(domain.getBrand().getBrand());
		entity.setPricing(domain.getPricing().getPricing());
		entity.setToyType(domain.getToyType());
		entity.setAgeRange(domain.getAgeRange());
		entity.setMaterial(domain.getMaterial());
		entity.setNumberOfPieces(domain.getNumberOfPieces());
		entity.setBatteryRequired(domain.isBatteryRequired());
		entity.setBatteryIncluded(domain.isBatteryIncluded());

		if (domain.getProductStatusPolicy() != null) {
			entity.setJustification(domain.getProductStatusPolicy().getJustification());
			if (domain.getProductStatusPolicy().getCategory() != null) {
				entity.setProduct_status(ProductStatusCategoryEntity.valueOf(
						domain.getProductStatusPolicy().getCategory().name()));
			}
		}

		entity.setMidias(domain.getMidia().stream().map(media -> toEntity(media, entity))
				.collect(Collectors.toList()));
		return entity;
	}

	private MidiaEntity toEntity(Media media, ToyEntity product) {
		MidiaEntity entity = new MidiaEntity();
		entity.setMidia_id(media.getId());
		entity.setUrl(media.getUrl());
		entity.setDescription(media.getDescription());
		entity.setProduct(product);
		return entity;
	}
}
