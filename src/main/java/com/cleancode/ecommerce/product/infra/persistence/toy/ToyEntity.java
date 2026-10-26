package com.cleancode.ecommerce.product.infra.persistence.toy;

import com.cleancode.ecommerce.product.domain.toy.AgeRange;
import com.cleancode.ecommerce.product.domain.toy.ToyType;
import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_toy")
@DiscriminatorValue("toy")
public class ToyEntity extends ProductEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "toy_type", nullable = false)
	private ToyType toyType;

	@Enumerated(EnumType.STRING)
	@Column(name = "age_range", nullable = false)
	private AgeRange ageRange;

	@Column(nullable = false)
	private String material;

	@Column(name = "number_of_pieces")
	private Integer numberOfPieces;

	@Column(name = "battery_required", nullable = false)
	private boolean batteryRequired;

	@Column(name = "battery_included", nullable = false)
	private boolean batteryIncluded;
}
