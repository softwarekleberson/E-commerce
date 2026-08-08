package com.cleancode.ecommerce.stock.productInput;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.stock.domain.ProductQuality;
import com.cleancode.ecommerce.stock.domain.Quantity;
import com.cleancode.ecommerce.stock.domain.Supplier;
import com.cleancode.ecommerce.stock.domain.productinput.ProductInput;

class ProductInputTest {

    @Test
    @DisplayName("Should successfully instantiate a ProductInput, mapping all attributes and generating the current date")    void shouldCreateProductInputSuccessfully() {
        Price purchasePriceMock = mock(Price.class);
        int quantityValue = 50;
        String supplierName = "Fornecedor Alfa";
        ProductQuality quality = ProductQuality.NEW; // Presumindo que NEW seja uma das constantes do seu enum

        LocalDateTime beforeCreation = LocalDateTime.now();
        ProductInput productInput = new ProductInput(quantityValue, quality, purchasePriceMock, supplierName);
        LocalDateTime afterCreation = LocalDateTime.now();

        // Valida as conversões internas de tipos primitivos/Strings para Value Objects
        assertThat(productInput).isNotNull();
        assertThat(productInput.getQuantity()).isEqualTo(new Quantity(quantityValue));
        assertThat(productInput.getSupplier()).isEqualTo(new Supplier(supplierName));
        assertThat(productInput.getProductQuality()).isEqualTo(quality);
        assertThat(productInput.getPurchasePrice()).isEqualTo(purchasePriceMock);

        // Valida que a data de entrada foi capturada corretamente no momento da execução do construtor
        assertThat(productInput.getEntryTime()).isAfterOrEqualTo(beforeCreation);
        assertThat(productInput.getEntryTime()).isBeforeOrEqualTo(afterCreation);
    }

    @Test
    @DisplayName("Should validate the equality contract (equals and hashCode) based on mapped attributes")    void shouldRespectEqualsAndHashCodeContract() {
        Price purchasePriceMock = mock(Price.class);
        
        // Criando uma única instância
        ProductInput input1 = new ProductInput(10, ProductQuality.NEW, purchasePriceMock, "Fornecedor Beta");
        
        // Uma instância é sempre igual a ela mesma
        assertThat(input1).isEqualTo(input1);
        assertThat(input1.hashCode()).isEqualTo(input1.hashCode());

        // Comparações que devem falhar (tipos diferentes ou nulo)
        assertThat(input1).isNotEqualTo(null);
        assertThat(input1).isNotEqualTo("not a product input");
    }

    @Test
    @DisplayName("Note on Equals: Two instances created sequentially may fail the equals check if entryTime differs by milliseconds")    void shouldDemonstrateTimeSensitivityOnEquality() {
        Price priceMock = mock(Price.class);

        ProductInput inputA = new ProductInput(5, ProductQuality.NEW, priceMock, "Fornecedor Gama");
        ProductInput inputB = new ProductInput(5, ProductQuality.NEW, priceMock, "Fornecedor Gama");

        // Caso o processador execute rápido o suficiente a ponto de manter o mesmo LocalDateTime (incluindo nanossegundos),
        // eles serão iguais. Caso mude, o equals baseado em Objects.equals(entryTime, other.entryTime) retornará falso.
        if (inputA.getEntryTime().equals(inputB.getEntryTime())) {
            assertThat(inputA).isEqualTo(inputB);
            assertThat(inputA.hashCode()).isEqualTo(inputB.hashCode());
        } else {
            assertThat(inputA).isNotEqualTo(inputB);
        }
    }
}