package com.cleancode.ecommerce.customer.infra.persistence.customer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {

	private String email;

}
