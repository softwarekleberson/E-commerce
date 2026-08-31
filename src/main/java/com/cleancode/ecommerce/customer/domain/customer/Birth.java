package com.cleancode.ecommerce.customer.domain.customer;

import java.time.LocalDate;
import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Birth {

	private LocalDate birth;

	public Birth(LocalDate birth) {
		if (birth == null || birth.isAfter(LocalDate.now())) {
			throw new IllegalDomainException("Date of birth cannot be in the future");
		}

		if (birth == null || birth.isAfter(LocalDate.now().minusYears(18)))
			throw new IllegalDomainException("You must be 18 years of age or older.");

		this.birth = birth;
	}

	public LocalDate getBirth() {
		return birth;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birth);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Birth other = (Birth) obj;
		return Objects.equals(birth, other.birth);
	}

	@Override
	public String toString() {
		return "Birth [birth=" + birth + "]";
	}
}