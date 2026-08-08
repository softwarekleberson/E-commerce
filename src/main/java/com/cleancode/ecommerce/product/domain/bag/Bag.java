package com.cleancode.ecommerce.product.domain.bag;

import java.util.List;
import java.util.Objects;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;

public class Bag extends Product {

	private Volume volume;
	private Color color;

	public Bag(Name name, Description description, Price price, ProductCategory category, Brand brand,
			List<Media> midias, Pricing pricing, Volume volume, Color color) {
		super(name, description, price, category, brand, midias, pricing);
		this.volume = volume;
		this.color = color;
	}

	public Bag(ProductId idProduct, boolean active, Name name, Description description, Price price,
			   ProductCategory category, Brand brand, List<Media> midias, Pricing pricing,
			   Volume volume, Color color) {
		super(idProduct, active, name, description, price, category, brand, midias, pricing);
		this.volume = volume;
		this.color = color;
	}

	public Volume getVolume() {
		return volume;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "Bag [volume=" + volume + ", color=" + color + "]";
	}
}