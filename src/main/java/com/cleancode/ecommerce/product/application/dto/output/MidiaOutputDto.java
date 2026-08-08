package com.cleancode.ecommerce.product.application.dto.output;

import java.io.Serializable;

public class MidiaOutputDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String url;
	private String description;

	public MidiaOutputDto() {
	}

	public MidiaOutputDto(String id, String url, String description) {
		this.id = id;
		this.url = url;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
