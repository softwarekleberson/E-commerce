package com.cleancode.ecommerce.replacement.infra.persistece;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_replacement")
public class ReplacementEntity {

	@Id
	private String id;
	
	@Column(name = "reservation_id")
	private String reservationId;
	
	@Enumerated(EnumType.STRING)
	private ReasonEntity reason;
	
	@Column(name = "`explain`") 
	private String explain;
	
	@Enumerated(EnumType.STRING)
	private StatusEntity status;
	
	@Column(name = "customer_id")
	private String customerId;
	
	private int quantity;
}
