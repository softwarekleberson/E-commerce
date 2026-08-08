package com.cleancode.ecommerce.stock.productOutput;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.order.domain.OrderId;
import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.stock.domain.productoutput.ProductOutput;

class ProductOutputTest {

    @Test
    @DisplayName("Should instantiate ProductOutput, automatically generating a new OrderId")    void shouldConstructWithAutomaticOrderId() {
        ProductId productIdMock = mock(ProductId.class);
        int quantity = 5;

        ProductOutput output = new ProductOutput(productIdMock, quantity);

        assertThat(output).isNotNull();
        assertThat(output.getOrderId()).isNotNull(); // ID gerado automaticamente
        assertThat(output.getProductId()).isEqualTo(productIdMock);
        assertThat(output.getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("Should instantiate ProductOutput, correctly mapping the Strings to their respective Value Objects")    void shouldConstructWithExplicitStrings() {
        String orderIdStr = "order-777";
        String productIdStr = "prod-999";
        int quantity = 12;

        ProductOutput output = new ProductOutput(orderIdStr, productIdStr, quantity);

        assertThat(output).isNotNull();
        assertThat(output.getOrderId()).isEqualTo(new OrderId(orderIdStr));
        assertThat(output.getProductId()).isEqualTo(new ProductId(productIdStr));
        assertThat(output.getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("Should ensure the equality contract (equals and hashCode) based on all class fields")    void shouldRespectEqualsAndHashCodeContract() {
        String orderId = "order-123";
        String productId = "prod-456";
        int quantity = 3;

        ProductOutput output1 = new ProductOutput(orderId, productId, quantity);
        ProductOutput output2 = new ProductOutput(orderId, productId, quantity);
        
        ProductOutput outputDifferentQty = new ProductOutput(orderId, productId, 99);
        ProductOutput outputDifferentOrder = new ProductOutput("order-999", productId, quantity);

        // Atributos idênticos geram objetos logicamente iguais
        assertThat(output1).isEqualTo(output2);
        assertThat(output1.hashCode()).isEqualTo(output2.hashCode());

        // Comparações que devem falhar por quebra de simetria de dados
        assertThat(output1).isNotEqualTo(outputDifferentQty);
        assertThat(output1).isNotEqualTo(outputDifferentOrder);
        assertThat(output1).isNotEqualTo(null);
        assertThat(output1).isNotEqualTo("not a product output object");
    }
}