package com.cleancode.ecommerce.product.infra.persistence.product;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type")
public abstract class ProductEntity {

	@Id
	protected String product_id;
	protected boolean active;
	protected String name;
	protected String description;
	protected BigDecimal price;

	@Enumerated(EnumType.STRING)
	protected TypeCoinEntity type_coin;

	@Enumerated(EnumType.STRING)
	protected ProductCategoryEntity category;
	protected String brand;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	protected List<MidiaEntity> midias;
	
	protected BigDecimal pricing;
	
	protected String justification;
	
	@Enumerated(EnumType.STRING)
	protected ProductStatusCategoryEntity product_status;

}