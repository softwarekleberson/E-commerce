package com.cleancode.ecommerce.replacement.infra.persistece;

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
	private String reservation_Id;
	
	@Enumerated(EnumType.STRING)
	private ReasonEntity reason;
	
	private String explain;
	
	@Enumerated(EnumType.STRING)
	private StatusEntity status;
}
