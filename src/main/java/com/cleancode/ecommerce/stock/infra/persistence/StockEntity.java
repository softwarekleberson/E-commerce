package com.cleancode.ecommerce.stock.infra.persistence;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "tb_stock")
public class StockEntity {

	@Id
	private String stock_id;
	
	@Column(name = "product_id")
	private String productId;
	
	private int total_quantity;
	
	@Column(name = "quantity_available")
	private int quantity_available;
	
	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	List<ReservationEntity> reservations;
	
	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	List<StockInputEntity> inputs;
	
	@OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	List<StockOutputEntity> outputs;
}