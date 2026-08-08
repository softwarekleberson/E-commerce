package com.cleancode.ecommerce.stock.infra.persistence;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_stock_output")
public class StockOutputEntity {

	@Id
	private String stock_output_id = UUID.randomUUID().toString();
	
	private String order_id;	
	private String product_id;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private StockEntity stock;
}