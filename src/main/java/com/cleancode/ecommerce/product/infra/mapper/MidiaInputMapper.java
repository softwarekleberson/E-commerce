package com.cleancode.ecommerce.product.infra.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.infra.persistence.product.MidiaEntity;

public class MidiaInputMapper {

	public static List<Media> toMidiaList(List<MidiaEntity> entities) {
		if (entities == null)
			return Collections.emptyList();

		return entities.stream().map(img -> new Media(img.getMidia_id(), img.getUrl(), img.getDescription()))
				.collect(Collectors.toList());
	}
}
