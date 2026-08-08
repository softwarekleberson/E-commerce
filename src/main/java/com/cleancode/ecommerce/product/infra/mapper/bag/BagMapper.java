package com.cleancode.ecommerce.product.infra.mapper.bag;

import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.product.domain.bag.Bag;
import com.cleancode.ecommerce.product.domain.bag.Color;
import com.cleancode.ecommerce.product.domain.bag.Volume;
import com.cleancode.ecommerce.product.infra.mapper.MidiaInputMapper;
import com.cleancode.ecommerce.product.infra.persistence.bag.BagEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.MidiaEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductCategoryEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductStatusCategoryEntity;
import com.cleancode.ecommerce.product.infra.persistence.product.TypeCoinEntity;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public class BagMapper {

	private BagMapper() {
	}

	public static BagEntity toEntity(Bag domain) {
		BagEntity entity = new BagEntity();

		entity.setProduct_id(domain.getProductId().getProductId());
		entity.setActive(domain.isActive());
		entity.setName(domain.getName().getName());
		entity.setDescription(domain.getDescription().getDescription());
		entity.setPrice(domain.getPrice().getPrice());
		entity.setType_coin(TypeCoinEntity.valueOf(domain.getPrice().getCoin().name()));
		entity.setCategory(ProductCategoryEntity.valueOf(domain.getProductCategory().name()));
		entity.setBrand(domain.getBrand().getBrand());

		List<MidiaEntity> imageEntities = domain.getMidia().stream().map(midia -> {
			MidiaEntity imageEntity = new MidiaEntity();
			imageEntity.setMidia_id(midia.getId());
			imageEntity.setUrl(midia.getUrl());
			imageEntity.setDescription(midia.getDescription());
			imageEntity.setProduct(entity);
			return imageEntity;
		}).collect(Collectors.toList());

		entity.setMidias(imageEntities);
		entity.setPricing(domain.getPricing().getPricing());
		if (domain.getProductStatusPolicy() != null) {
			entity.setJustification(domain.getProductStatusPolicy().getJustification());

			if (domain.getProductStatusPolicy().getCategory() != null) {
				entity.setProduct_status(
						ProductStatusCategoryEntity.valueOf(domain.getProductStatusPolicy().getCategory().name()));
			}
		}

		entity.setVolume(domain.getVolume().getVolume());
		entity.setColor(domain.getColor().getColor());

		return entity;
	}

	public static Bag toDomain(BagEntity entity) {
		return new Bag(new ProductId(entity.getProduct_id()), entity.isActive(), new Name(entity.getName()),
				new Description(entity.getDescription()),
				new Price(entity.getPrice(), TypeCoin.valueOf(entity.getType_coin().name())),
				ProductCategory.valueOf(entity.getCategory().name()), new Brand(entity.getBrand()),
				MidiaInputMapper.toMidiaList(entity.getMidias()), new Pricing(entity.getPricing()),
				new Volume(entity.getVolume()), new Color(entity.getColor()));
	}
}