package com.cleancode.ecommerce.product.domain.toy;

import java.util.List;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;

public class Toy extends Product {

	private final ToyType toyType;
	private final AgeRange ageRange;
	private final String material;
	private final Integer numberOfPieces;
	private final boolean batteryRequired;
	private final boolean batteryIncluded;

	public Toy(Name name, Description description, Price price, Brand brand, List<Media> media,
			Pricing pricing, ToyType toyType, AgeRange ageRange, String material, Integer numberOfPieces,
			boolean batteryRequired, boolean batteryIncluded) {
		super(name, description, price, ProductCategory.TOYS, brand, media, pricing);
		this.toyType = toyType;
		this.ageRange = ageRange;
		this.material = material;
		this.numberOfPieces = numberOfPieces;
		this.batteryRequired = batteryRequired;
		this.batteryIncluded = batteryIncluded;
	}

	public Toy(ProductId productId, boolean active, Name name, Description description, Price price,
			Brand brand, List<Media> media, Pricing pricing, ToyType toyType, AgeRange ageRange,
			String material, Integer numberOfPieces, boolean batteryRequired, boolean batteryIncluded) {
		super(productId, active, name, description, price, ProductCategory.TOYS, brand, media, pricing);
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
}
