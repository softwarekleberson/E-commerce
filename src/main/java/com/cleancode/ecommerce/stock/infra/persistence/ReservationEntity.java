package com.cleancode.ecommerce.stock.infra.persistence;

import java.time.LocalDateTime;

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
@Table(name = "tb_reservation")
public class ReservationEntity {

	@Id
	private String reservation_id;
	
	private String cart_id;	
	private String customer_id;
	private int quantity;	
	private LocalDateTime reservation_time;
	
	@Enumerated(EnumType.STRING)
	private ReserveStatusEntity reserve_status;
	
	@ManyToOne
	@JoinColumn(name = "stock_id")
	private StockEntity stock;
}