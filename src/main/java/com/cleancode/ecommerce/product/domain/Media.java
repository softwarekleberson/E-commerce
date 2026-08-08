package com.cleancode.ecommerce.product.domain;

import java.util.Objects;
import java.util.UUID;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Media {

	private String id;
	private String url;
	private String description;

	public Media(String url, String description) {
		String regex = "\\b((https?:\\/\\/)?(www\\.)?[\\w\\-]+\\.[\\w\\-]+(\\.[\\w\\-]+)?([\\/\\w\\-\\.\\?\\=\\&\\#]*)?)\\b";
		if (!url.matches(regex)) {
			throw new IllegalDomainException("Url not be valid");
		}

		if (description == null || description.isBlank()) {
			throw new IllegalDomainException("Description not be valid");
		}

		this.id = UUID.randomUUID().toString();
		this.url = url;
		this.description = description;
	}

	public Media(String id, String url, String description) {
		this.id = id;
		this.url = url;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		return Objects.equals(description, other.description) && Objects.equals(url, other.url);
	}

	@Override
	public String toString() {
		return "Midia [id=" + id + ", url=" + url + ", description=" + description + "]";
	}
}