package com.cleancode.ecommerce.product.bag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.Brand;
import com.cleancode.ecommerce.product.domain.Description;
import com.cleancode.ecommerce.product.domain.Media;
import com.cleancode.ecommerce.product.domain.Pricing;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.bag.Bag;
import com.cleancode.ecommerce.product.domain.bag.Color;
import com.cleancode.ecommerce.product.domain.bag.Volume;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public class BagTest {

	private Bag bag;

	@BeforeEach
	void setUp() {
		bag = new Bag(new Name("Bolsa X"), new Description("Bolsa impermeável"),
				new Price(BigDecimal.valueOf(250.0), TypeCoin.DOLAR), ProductCategory.BAG, new Brand("Nike"),
				new LinkedList<>(List.of(new Media("https://site.com/img.jpg", "Imagem bolsa"))),
				new Pricing(BigDecimal.valueOf(0.10)), new Volume(35), new Color("Preto"));
	}

	@Test
	void shouldCreateBagCorrectly() {
		assertEquals("Bolsa X", bag.getName().getName());
		assertEquals("Bolsa impermeável", bag.getDescription().getDescription());
		assertEquals(BigDecimal.valueOf(250.0), bag.getPrice().getPrice());
		assertEquals(TypeCoin.DOLAR, bag.getPrice().getCoin());
		assertEquals(35, bag.getVolume().getVolume());
		assertEquals("Preto", bag.getColor().getColor());
		assertEquals(1, bag.getMidia().size());
	}

	@Test
	void shouldNotBeEqualIfVolumeOrColorIsDifferent() {
		Bag differentBag = new Bag(new Name("Outra Bolsa"), new Description("Outra descrição"),
				new Price(BigDecimal.valueOf(300), TypeCoin.DOLAR), ProductCategory.BAG, new Brand("Adidas"), List.of(),
				new Pricing(BigDecimal.valueOf(0.10)), new Volume(40), new Color("Preto"));

		assertNotEquals(bag, differentBag);
	}

	@Test
	void shouldUpdateProductFields() {
		bag.reviseDetails("Nova descrição", "Novo Nome");

		assertEquals("Nova descrição", bag.getDescription().getDescription());
		assertEquals("Novo Nome", bag.getName().getName());
	}

	@Test
	void shouldUpdateOnlyName() {
		String oldDescription = bag.getDescription().getDescription();
		BigDecimal oldPrice = bag.getPrice().getPrice();

		bag.reviseDetails(null, "Nome Atualizado");

		assertEquals("Nome Atualizado", bag.getName().getName());
		assertEquals(oldDescription, bag.getDescription().getDescription());
		assertEquals(oldPrice, bag.getPrice().getPrice());
	}

	@Test
	void shouldUpdateMidiaById() {
		String idExistente = bag.getMidia().get(0).getId();

		bag.replaceMedia(idExistente, "https://image.com/updated.jpg", "Imagem atualizada");

		assertEquals(1, bag.getMidia().size());
		assertEquals("Imagem atualizada", bag.getMidia().get(0).getDescription());
		assertEquals("https://image.com/updated.jpg", bag.getMidia().get(0).getUrl());
		assertNotEquals(idExistente, bag.getMidia().get(0).getId());
	}

	@Test
	void shouldThrowExceptionWhenMidiaIdNotFound() {
		String nonExistentId = "id-invalido";

		IllegalDomainException exception = assertThrows(IllegalDomainException.class,
				() -> bag.replaceMedia(nonExistentId, "https://nova.com/img.jpg", "Nova Imagem"));

		assertEquals("Midia with ID 'id-invalido' not found.", exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentMidia() {
		String nonExistentId = "inexistente-123";

		IllegalDomainException exception = assertThrows(IllegalDomainException.class,
				() -> bag.removeMediaById(nonExistentId));

		assertEquals("Midia with ID 'inexistente-123' not found.", exception.getMessage());
	}

	@Test
	void shouldDeleteMidiaSuccessfully() {
		String id = bag.getMidia().get(0).getId();
		assertEquals(1, bag.getMidia().size());

		bag.removeMediaById(id);
		assertEquals(0, bag.getMidia().size());
	}
}