package com.cleancode.ecommerce.product.application.dto.output;

import com.cleancode.ecommerce.product.domain.toy.AgeRange;
import com.cleancode.ecommerce.product.domain.toy.ToyType;

public class ListToyDto extends ListProductDto {

	private static final long serialVersionUID = 1L;
	private ToyType toyType;
	private AgeRange ageRange;
	private String material;
	private Integer numberOfPieces;
	private boolean batteryRequired;
	private boolean batteryIncluded;

	public ToyType getToyType() { return toyType; }
	public void setToyType(ToyType toyType) { this.toyType = toyType; }
	public AgeRange getAgeRange() { return ageRange; }
	public void setAgeRange(AgeRange ageRange) { this.ageRange = ageRange; }
	public String getMaterial() { return material; }
	public void setMaterial(String material) { this.material = material; }
	public Integer getNumberOfPieces() { return numberOfPieces; }
	public void setNumberOfPieces(Integer numberOfPieces) { this.numberOfPieces = numberOfPieces; }
	public boolean isBatteryRequired() { return batteryRequired; }
	public void setBatteryRequired(boolean batteryRequired) { this.batteryRequired = batteryRequired; }
	public boolean isBatteryIncluded() { return batteryIncluded; }
	public void setBatteryIncluded(boolean batteryIncluded) { this.batteryIncluded = batteryIncluded; }
}
