package com.cleancode.ecommerce.product.product;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.ProductId;

public class ProductIdTest {

	@Test
	void deveGerarUUIDQuandoConstrutorSemParametros() {
		ProductId id = new ProductId();
		assertNotNull(id.getProductId());
		assertDoesNotThrow(() -> java.util.UUID.fromString(id.getProductId()));
	}

	@Test
	void deveAceitarValorEspecifico() {
		String value = "12345-abc";
		ProductId id = new ProductId(value);
		assertEquals(value, id.getProductId());
	}

	@Test
	void equalsHashCodeDevemFuncionarCorretamente() {
		String value = "same-id";
		ProductId id1 = new ProductId(value);
		ProductId id2 = new ProductId(value);
		ProductId id3 = new ProductId();

		assertEquals(id1, id2);
		assertEquals(id1.hashCode(), id2.hashCode());

		assertNotEquals(id1, id3);
		assertNotEquals(id1.hashCode(), id3.hashCode());
	}

	@Test
	void equalsDeveRetornarFalseParaObjetoDeOutroTipo() {
		ProductId id = new ProductId();
		assertNotEquals(id, "string qualquer");
	}

	@Test
	void equalsDeveRetornarFalseParaNull() {
		ProductId id = new ProductId();
		assertNotEquals(null, id);
	}
}
