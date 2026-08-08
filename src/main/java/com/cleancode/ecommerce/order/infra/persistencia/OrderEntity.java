package com.cleancode.ecommerce.order.infra.persistencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cleancode.ecommerce.cart.infra.persistence.TypeCoinEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tb_orders")
public class OrderEntity {

	@Id
	private String order_Id;
    private String customer_Id;
    private String delivery_Id;
    private LocalDateTime created_At;
    
    @OneToMany(
			mappedBy = "order",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<OrderItemEntity> order_itens = new ArrayList<>();
    
    private BigDecimal value;
    
    @Enumerated(EnumType.STRING)
	private TypeCoinEntity type_Coin;	
	
    @Enumerated(EnumType.STRING)
	private OrderStatusEntity status;
}
