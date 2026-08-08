package com.cleancode.ecommerce.customer.infra.persistence.customer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneEntity {

	private String area_code ;
	private String phone_number ;
	
	@Enumerated(EnumType.STRING)
	private PhoneTypeEntity phone_type ;
}
