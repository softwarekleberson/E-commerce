package com.cleancode.ecommerce.product.application.dto.output;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

@JsonSubTypes
({ @JsonSubTypes.Type(value = ListBookDto.class, name = "book"),
   @JsonSubTypes.Type(value = ListBagDto.class, name = "bag") 
})
public abstract class ListProductDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
	private TypeCoin typeCoin;
	private ProductCategory category;
	private String brand;
	private List<MidiaOutputDto> midias;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public TypeCoin getTypeCoin() {
		return typeCoin;
	}

	public void setTypeCoin(TypeCoin typeCoin) {
		this.typeCoin = typeCoin;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<MidiaOutputDto> getMidias() {
		return midias;
	}

	public void setMidias(List<MidiaOutputDto> midias) {
		this.midias = midias;
	}
}