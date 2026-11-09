package com.cleancode.ecommerce.product.domain;

import java.util.Objects;
import java.util.UUID;
import java.net.URI;
import java.net.URISyntaxException;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Media {

	private String id;
	private String url;
	private String description;

	public Media(String url, String description) {
		if (!isValidUrl(url)) {
			throw new IllegalDomainException("Url not be valid");
		}

		if (description == null || description.isBlank()) {
			throw new IllegalDomainException("Description not be valid");
		}

		this.id = UUID.randomUUID().toString();
		this.url = url;
		this.description = description;
	}

	private boolean isValidUrl(String url) {
		if (url == null || url.isBlank()) {
			return false;
		}

		try {
			URI uri = new URI(url);
			String scheme = uri.getScheme();
			return uri.getHost() != null && ("http".equalsIgnoreCase(scheme)
					|| "https".equalsIgnoreCase(scheme));
		} catch (URISyntaxException exception) {
			return false;
		}
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
