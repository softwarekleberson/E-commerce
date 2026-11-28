package com.cleancode.ecommerce.recommendation.infra.client;

import java.util.List;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RagRecommendationClient {
	private static final Logger log = LoggerFactory.getLogger(RagRecommendationClient.class);
	private static final int CATALOG_HEADER_CHUNK_SIZE = 3000;
	private final HttpClient httpClient;
	private final String baseUrl;
	private final ObjectMapper objectMapper;

	public RagRecommendationClient(ObjectMapper objectMapper,
			@Value("${recommendation.rag.base-url:http://localhost:8000}") String baseUrl) {
		this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
		this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
		this.objectMapper = objectMapper;
	}

	public void replaceIndex(List<CatalogProduct> products) {
		String payload = jsonBody(new IndexRequest(products));
		String encodedCatalog = Base64.getUrlEncoder().withoutPadding().encodeToString(gzip(payload));
		String syncId = UUID.randomUUID().toString();
		int chunkCount = (encodedCatalog.length() + CATALOG_HEADER_CHUNK_SIZE - 1) / CATALOG_HEADER_CHUNK_SIZE;
		for (int chunkIndex = 0; chunkIndex < chunkCount; chunkIndex++) {
			int start = chunkIndex * CATALOG_HEADER_CHUNK_SIZE;
			String chunk = encodedCatalog.substring(start,
					Math.min(start + CATALOG_HEADER_CHUNK_SIZE, encodedCatalog.length()));
			HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/index/chunk"))
					.header("Accept", "application/json")
					.header("X-Catalog-Sync-Id", syncId)
					.header("X-Catalog-Chunk-Index", Integer.toString(chunkIndex))
					.header("X-Catalog-Chunk-Count", Integer.toString(chunkCount))
					.header("X-Catalog-Chunk-Data", chunk)
					.PUT(HttpRequest.BodyPublishers.noBody())
					.build();
			send(request);
		}
	}

	public RagAnswer recommend(String message, List<PurchasePreference> purchasePreferences) {
		log.debug("Sending chat request to RAG service with {} purchase preferences", purchasePreferences.size());
		String requestBody = jsonBody(new RecommendRequest(message, purchasePreferences));
		HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/recommend"))
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
				.build();
		String response = send(request);
		try {
			return objectMapper.readValue(response, RagAnswer.class);
		} catch (JsonProcessingException exception) {
			throw new IllegalStateException("RAG service returned an invalid recommendation response", exception);
		}
	}

	private String send(HttpRequest request) {
		try {
			HttpResponse<String> response = httpClient.send(request,
					HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			if (response.statusCode() < 200 || response.statusCode() >= 300) {
				throw new IllegalStateException("RAG service returned HTTP " + response.statusCode() + ": "
						+ response.body());
			}
			return response.body();
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interrupted while calling the RAG service", exception);
		} catch (IOException exception) {
			throw new IllegalStateException("Could not reach the RAG service", exception);
		}
	}

	private byte[] gzip(String value) {
		try (ByteArrayOutputStream output = new ByteArrayOutputStream();
				GZIPOutputStream gzip = new GZIPOutputStream(output)) {
			gzip.write(value.getBytes(StandardCharsets.UTF_8));
			gzip.finish();
			return output.toByteArray();
		} catch (IOException exception) {
			throw new IllegalArgumentException("Could not compress product catalog for RAG service", exception);
		}
	}

	private String jsonBody(Object request) {
		try {
			return objectMapper.writeValueAsString(request);
		} catch (JsonProcessingException exception) {
			throw new IllegalArgumentException("Could not serialize RAG request as JSON", exception);
		}
	}

	public record CatalogProduct(String id, String name, String description, String category, String brand,
			String price, String currency) {
	}
	public record IndexRequest(List<CatalogProduct> products) {
	}
	public record PurchasePreference(String category, int purchaseCount) {
	}
	public record RecommendRequest(String message, List<PurchasePreference> purchasePreferences) {
	}
	public record RagProduct(String id, String reason) {
	}
	public record RagAnswer(String answer, List<RagProduct> recommendations) {
	}
}
