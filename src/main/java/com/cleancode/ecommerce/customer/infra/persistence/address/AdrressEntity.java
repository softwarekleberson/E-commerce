package com.cleancode.ecommerce.customer.infra.persistence.address;

import java.util.Objects;

import com.cleancode.ecommerce.customer.infra.persistence.customer.CustomerEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_address")
public abstract class AdrressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long address_id;
	
	protected String public_id;
	protected Boolean main;
	protected String receiver;
	protected String street;
	protected String number;
	protected String neighborhood;
	protected String zip_code;
	protected String observation;
	protected String street_type;
	protected String residence_type;
	protected String city;
	protected String state;
	protected String country;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	protected CustomerEntity customer;

	@Override
	public int hashCode() {
		return Objects.hash(address_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdrressEntity other = (AdrressEntity) obj;
		return Objects.equals(address_id, other.address_id);
	}
}