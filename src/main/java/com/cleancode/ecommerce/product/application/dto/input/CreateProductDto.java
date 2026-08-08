package com.cleancode.ecommerce.product.application.dto.input;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "product_type"

)

@JsonSubTypes({ @JsonSubTypes.Type(value = CreateBoockDto.class, name = "book"),
@JsonSubTypes.Type(value = CreateBagDto.class, name = "bag") 
})
public abstract class CreateProductDto implements ProductCreatableInterface {

	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Description is required")
	private String description;
	
	private BigDecimal price;
	
	@NotNull(message = "Type coin is required")
	private TypeCoin typeCoin;
	
	@NotNull(message = "Product category is required")
	private ProductCategory category;
	
	@NotBlank(message = "Brand is required")
	private String brand;
	
	@Valid
	@NotNull(message = "Midia is required")
	private List<MidiaInputDto> midias;
	
	@NotNull(message = "Pricing is required")
	private BigDecimal pricing;

	public CreateProductDto(String name, String description, TypeCoin typeCoin,
			ProductCategory category, String brand, List<MidiaInputDto> midias, BigDecimal pricing) {

		this.name = name;
		this.description = description;
		this.price = BigDecimal.ZERO;
		this.typeCoin = typeCoin;
		this.category = category;
		this.brand = brand;
		this.midias = midias;
		this.pricing = pricing;
	}

	protected List<Media> extractedMidia() {
		List<Media> midiasDomain = getMidias().stream()
				.map(midiaDto -> new Media(midiaDto.getUrl(), midiaDto.getDescription())).collect(Collectors.toList());
		return midiasDomain;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public TypeCoin getTypeCoin() {
		return typeCoin;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public List<MidiaInputDto> getMidias() {
		return midias;
	}

	public BigDecimal getPricing() {
		return pricing;
	}
}