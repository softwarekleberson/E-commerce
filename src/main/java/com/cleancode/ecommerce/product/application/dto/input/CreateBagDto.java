package com.cleancode.ecommerce.product.application.dto.input;

import java.math.BigDecimal;
import java.util.List;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.bag.Bag;
import com.cleancode.ecommerce.product.domain.bag.Color;
import com.cleancode.ecommerce.product.domain.bag.Volume;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateBagDto extends CreateProductDto {

	@NotBlank(message = "Color is required")
	private String color;
	
	@Min(0)
	private double volume;

	public CreateBagDto(String name, String description, TypeCoin typeCoin, ProductCategory category,
			String brand, List<MidiaInputDto> midias, BigDecimal pricing, String color, double volume) {
		super(name, description, typeCoin, ProductCategory.BAG, brand, midias, pricing);
		this.color = color;
		this.volume = volume;
	}

	public String getColor() {
		return color;
	}

	public double getVolume() {
		return volume;
	}

	@Override
	public Product toProduct() {
		List<Media> midiasDomain = extractedMidia();

		return new Bag(new Name(getName()), new Description(getDescription()), new Price(getPrice(), getTypeCoin()),
				getCategory(), new Brand(getBrand()), midiasDomain, new Pricing(getPricing()), new Volume(volume),
				new Color(color));
	}
}