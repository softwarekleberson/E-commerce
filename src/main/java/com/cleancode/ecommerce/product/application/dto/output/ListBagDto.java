package com.cleancode.ecommerce.product.application.dto.output;

import java.io.Serializable;

public class ListBagDto extends ListProductDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String color;
	private double volume;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
}