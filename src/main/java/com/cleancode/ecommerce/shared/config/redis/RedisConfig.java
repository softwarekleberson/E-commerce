package com.cleancode.ecommerce.shared.config.redis;

import java.time.Duration;
import java.io.IOException;
import java.util.Map;

import com.cleancode.ecommerce.product.infra.service.cache.ProductCacheNames;
import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.application.dto.output.ListBagDto;
import com.cleancode.ecommerce.product.application.dto.output.ListBookDto;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/*
 * Essa classe define a configuração do Spring Cache utilizando
 * o Redis como armazenamento centralizado, determinando regras
 * de expiração, prefixo de chaves e a forma como os dados do Java
 * são convertidos em JSON para serem salvos.
 * 
 */

@Configuration
public class RedisConfig {

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.registerModule(new JavaTimeModule());

		objectMapper.activateDefaultTyping(
				LaissezFaireSubTypeValidator.instance,
				ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.PROPERTY
		);

		GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofHours(1))
				.disableCachingNullValues()
				.prefixCacheNameWith("ecommerce:")
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
				)
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer)
				);
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory,
			RedisCacheConfiguration cacheConfiguration) {
		ObjectMapper productObjectMapper = new ObjectMapper();
		productObjectMapper.registerModule(new JavaTimeModule());
		RedisSerializer<ListProductDto> productSerializer = new ProductRedisSerializer(productObjectMapper);
		RedisSerializer<PageResponse<ListProductDto>> productPageSerializer =
				new ProductPageRedisSerializer(productObjectMapper);

		RedisCacheConfiguration productValueConfiguration = cacheConfiguration
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(productSerializer));
		RedisCacheConfiguration productPageConfiguration = cacheConfiguration
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(productPageSerializer));

		Map<String, RedisCacheConfiguration> typedCacheConfigurations = Map.of(
				ProductCacheNames.BY_ID, productValueConfiguration,
				ProductCacheNames.ACTIVE_PAGES, productPageConfiguration,
				ProductCacheNames.INACTIVE_PAGES, productPageConfiguration);

		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(cacheConfiguration)
				.withInitialCacheConfigurations(typedCacheConfigurations)
				.build();
	}

	private static class ProductRedisSerializer implements RedisSerializer<ListProductDto> {
		private final ObjectMapper mapper;

		private ProductRedisSerializer(ObjectMapper mapper) {
			this.mapper = mapper;
		}

		@Override
		public byte[] serialize(ListProductDto product) throws SerializationException {
			if (product == null) return new byte[0];
			try {
				return mapper.writeValueAsBytes(toCacheNode(mapper, product));
			} catch (IOException exception) {
				throw new SerializationException("Could not serialize cached product", exception);
			}
		}

		@Override
		public ListProductDto deserialize(byte[] source) throws SerializationException {
			if (source == null || source.length == 0) return null;
			try {
				return fromCacheNode(mapper, mapper.readTree(source));
			} catch (IOException exception) {
				throw new SerializationException("Could not deserialize cached product", exception);
			}
		}
	}

	private static class ProductPageRedisSerializer implements RedisSerializer<PageResponse<ListProductDto>> {
		private final ObjectMapper mapper;

		private ProductPageRedisSerializer(ObjectMapper mapper) {
			this.mapper = mapper;
		}

		@Override
		public byte[] serialize(PageResponse<ListProductDto> page) throws SerializationException {
			if (page == null) return new byte[0];
			try {
				ObjectNode root = mapper.createObjectNode();
				ArrayNode content = root.putArray("content");
				for (ListProductDto product : page.getContent()) content.add(toCacheNode(mapper, product));
				root.put("page", page.getPage());
				root.put("size", page.getSize());
				root.put("totalElements", page.getTotalElements());
				root.put("totalPages", page.getTotalPages());
				return mapper.writeValueAsBytes(root);
			} catch (IOException exception) {
				throw new SerializationException("Could not serialize cached product page", exception);
			}
		}

		@Override
		public PageResponse<ListProductDto> deserialize(byte[] source) throws SerializationException {
			if (source == null || source.length == 0) return null;
			try {
				JsonNode root = mapper.readTree(source);
				ArrayNode cachedContent = (ArrayNode) root.path("content");
				java.util.List<ListProductDto> content = new java.util.ArrayList<>(cachedContent.size());
				for (JsonNode product : cachedContent) content.add(fromCacheNode(mapper, product));
				return new PageResponse<>(content, root.path("page").asInt(), root.path("size").asInt(),
						root.path("totalElements").asLong(), root.path("totalPages").asInt());
			} catch (IOException | ClassCastException exception) {
				throw new SerializationException("Could not deserialize cached product page", exception);
			}
		}
	}

	private static ObjectNode toCacheNode(ObjectMapper mapper, ListProductDto product) {
		ObjectNode node = mapper.valueToTree(product);
		node.remove("type");
		if (product instanceof ListBookDto) node.put("@cacheType", "book");
		else if (product instanceof ListBagDto) node.put("@cacheType", "bag");
		else throw new SerializationException("Unsupported product DTO type: " + product.getClass().getName());
		return node;
	}

	private static ListProductDto fromCacheNode(ObjectMapper mapper, JsonNode node) throws IOException {
		String type = node.path("@cacheType").asText();
		ObjectNode productNode = (ObjectNode) node;
		productNode.remove("@cacheType");
		productNode.put("type", type);
		return switch (type) {
			case "book" -> mapper.treeToValue(productNode, ListBookDto.class);
			case "bag" -> mapper.treeToValue(productNode, ListBagDto.class);
			default -> throw new SerializationException("Unsupported cached product type: " + type);
		};
	}
}
