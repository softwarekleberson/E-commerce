package com.cleancode.ecommerce.shared.kernel.domain;

import com.cleancode.ecommerce.shared.kernel.exception.IllegalDomainException;

public class Name {

	private final String name;
	
	public Name(String name) {
		if(name == null || name.trim().isEmpty()) {
			throw new IllegalDomainException("Name not be null");
		}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Name [name=" + name + "]";
	}
}
