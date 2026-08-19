package com.cleancode.ecommerce.shared.config.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/*
 * Essa classe é um serializador customizado para o Redis. Ela implementa
 * a interface RedisSerializer<Object> do Spring Data Redis para converter
 * objetos Java em bytes (no formato JSON) ao salvar no Redis e fazer o
 * caminho inverso (converter os bytes do Redis de volta para objetos Java)
 * ao ler os dados.
 *
 * */

public class GenericFastJsonRedisSerializer implements RedisSerializer<Object> {

	private static final byte[] EMPTY_ARRAY = new byte[0];

	private final ObjectMapper objectMapper;

	public GenericFastJsonRedisSerializer() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
				ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);
	}

	@Override
	public byte[] serialize(Object value) throws SerializationException {
		if (value == null) {
			return EMPTY_ARRAY;
		}
		try {
			return objectMapper.writeValueAsString(value).getBytes(StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new SerializationException("Erro ao serializar para Redis", e);
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		try {
			String json = new String(bytes, StandardCharsets.UTF_8);
			return objectMapper.readValue(json, Object.class);
		} catch (Exception e) {
			throw new SerializationException("Erro ao desserializar do Redis", e);
		}
	}
}