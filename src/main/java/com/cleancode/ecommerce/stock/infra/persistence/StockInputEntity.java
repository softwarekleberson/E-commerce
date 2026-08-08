package com.cleancode.ecommerce.stock.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.cleancode.ecommerce.shared.kernel.TypeCoin;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tb_stock_input")
public class StockInputEntity {

	@Id
	private String stock_input_id = UUID.randomUUID().toString();	
	private int quantity;
	
	@Enumerated(EnumType.STRING)
	private ProductQualityEntity product_quality;	
	
	private LocalDateTime entry_time = LocalDateTime.now();
	
	private BigDecimal purchase_price;
	
	@Enumerated(EnumType.STRING)
	private TypeCoin coin;
	private String supplier;
	
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private StockEntity stock;
}