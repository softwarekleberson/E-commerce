package com.cleancode.ecommerce.product.domain.books;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Page {

	public final int NUMBER_MIM_PAGE = 1;
	private final int page;
	
	public Page(int page) {
		if(page < NUMBER_MIM_PAGE) {
			throw new IllegalDomainException("Number page not be less than 0");
		}
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	@Override
	public int hashCode() {
		return Objects.hash(page);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		return page == other.page;
	}
}