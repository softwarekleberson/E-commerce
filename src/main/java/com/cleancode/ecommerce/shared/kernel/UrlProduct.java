package com.cleancode.ecommerce.shared.kernel;

import java.util.Objects;

public class UrlProduct {

	private final String urlProduct;
	
	public UrlProduct(String urlProduct) {
		this.urlProduct = urlProduct;
	}
	
	public String getUrlProduct() {
		return urlProduct;
	}

	@Override
	public int hashCode() {
		return Objects.hash(urlProduct);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrlProduct other = (UrlProduct) obj;
		return Objects.equals(urlProduct, other.urlProduct);
	}
}
