package com.cleancode.ecommerce.order.infra.persistencia;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_order_items")
public class OrderItemEntity {

	@Id
	private String order_item_id = UUID.randomUUID().toString();
	
	private String product_id;
	private BigDecimal price;
	private int quantity;
	private BigDecimal subtotal;
	private String reservation_id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderEntity order; 
}
