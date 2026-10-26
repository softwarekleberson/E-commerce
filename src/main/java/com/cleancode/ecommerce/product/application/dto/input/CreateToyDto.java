package com.cleancode.ecommerce.product.application.dto.input;

import java.math.BigDecimal;
import java.util.List;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.toy.AgeRange;
import com.cleancode.ecommerce.product.domain.toy.Toy;
import com.cleancode.ecommerce.product.domain.toy.ToyType;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateToyDto extends CreateProductDto {

	@NotNull(message = "Toy type is required")
	private ToyType toyType;

	@NotNull(message = "Age range is required")
	private AgeRange ageRange;

	@NotBlank(message = "Material is required")
	private String material;

	@Min(value = 1, message = "Number of pieces must be at least 1")
	private Integer numberOfPieces;

	private boolean batteryRequired;
	private boolean batteryIncluded;

	public CreateToyDto(String name, String description, TypeCoin typeCoin, String brand,
			List<MidiaInputDto> midias, BigDecimal pricing, ToyType toyType, AgeRange ageRange,
			String material, Integer numberOfPieces, boolean batteryRequired, boolean batteryIncluded) {
		super(name, description, typeCoin, ProductCategory.TOYS, brand, midias, pricing);
		this.toyType = toyType;
		this.ageRange = ageRange;
		this.material = material;
		this.numberOfPieces = numberOfPieces;
		this.batteryRequired = batteryRequired;
		this.batteryIncluded = batteryIncluded;
	}

	public ToyType getToyType() { return toyType; }
	public AgeRange getAgeRange() { return ageRange; }
	public String getMaterial() { return material; }
	public Integer getNumberOfPieces() { return numberOfPieces; }
	public boolean isBatteryRequired() { return batteryRequired; }
	public boolean isBatteryIncluded() { return batteryIncluded; }

	@Override
	public Product toProduct() {
		return new Toy(new Name(getName()), new Description(getDescription()),
				new Price(getPrice(), getTypeCoin()), new Brand(getBrand()), extractedMidia(),
				new Pricing(getPricing()), toyType, ageRange, material, numberOfPieces,
				batteryRequired, batteryIncluded);
	}
}
