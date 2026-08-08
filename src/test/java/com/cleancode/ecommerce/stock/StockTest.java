package com.cleancode.ecommerce.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.stock.domain.ProductQuality;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.StockId;
import com.cleancode.ecommerce.stock.domain.exception.IllegalReservationException;
import com.cleancode.ecommerce.stock.domain.productinput.ProductInput;
import com.cleancode.ecommerce.stock.domain.productoutput.ProductOutput;
import com.cleancode.ecommerce.stock.domain.reservation.Reservations;
import com.cleancode.ecommerce.stock.domain.reservation.ReserveStatus;

class StockTest {

    @Test
    @DisplayName("Should initialize inventory from a product ID and start at zero by default")
    void shouldConstructWithProductIdOnly() {
        Stock stock = new Stock("product-123");

        assertThat(stock.getStockId()).isNotNull();
        assertThat(stock.getProductId()).isEqualTo(new ProductId("product-123"));
        assertThat(stock.getTotalQuantity()).isEqualTo(0);
        assertThat(stock.getQuantityAvailable()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should initialize inventory with full constructor and set corresponding available quantity")
    void shouldConstructWithFullParameters() {
        StockId stockId = new StockId();
        ProductId productId = new ProductId("prod-abc");
        
        Stock stock = new Stock(stockId, productId, 100);

        assertThat(stock.getStockId()).isEqualTo(stockId);
        assertThat(stock.getProductId()).isEqualTo(productId);
        assertThat(stock.getTotalQuantity()).isEqualTo(100);
        assertThat(stock.getQuantityAvailable()).isEqualTo(100);
    }

    @Test
    @DisplayName("Should sum quantities and generate a valid ProductInput when processing a batch entry")
    void shouldAddProductInputWithParametersSuccessfully() {
        Stock stock = new Stock("prod-123");
        Price priceMock = mock(Price.class);

        stock.addProductInput(15, ProductQuality.NEW, priceMock, "Fornecedor Parceiro");

        assertThat(stock.getTotalQuantity()).isEqualTo(15);
        assertThat(stock.getQuantityAvailable()).isEqualTo(15);
        assertThat(stock.getProductInput()).hasSize(1);
        
        ProductInput inputMovement = stock.getProductInput().get(0);
        assertThat(inputMovement.getQuantity().getQuantity()).isEqualTo(15);
        assertThat(inputMovement.getSupplier().getSupplier()).isEqualTo("Fornecedor Parceiro");
    }

    @Test
    @DisplayName("Should throw exception when attempting to add a product entry with a quantity less than or equal to zero")
    void shouldThrowExceptionWhenProductInputQuantityIsInvalid() {
        Stock stock = new Stock("prod-123");
        Price priceMock = mock(Price.class);

        assertThatThrownBy(() -> stock.addProductInput(0, ProductQuality.NEW, priceMock, "Fornecedor"))
                .isInstanceOf(IllegalDomainException.class)
                .hasMessageContaining("Quantity must be positive");
    }

    @Test
    @DisplayName("Should successfully create a new reservation when sufficient stock is available")
    void shouldCreateReservationSuccessfully() {
        Stock stock = new Stock(new StockId(), new ProductId("prod-123"), 10);

        Reservations reservation = stock.reservation("cart-999", "customer-111", 4);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getQuantity()).isEqualTo(4);
        assertThat(reservation.getReserveStatus()).isEqualTo(ReserveStatus.ACTIVE);
        
        // Valida que o estoque disponível deduziu a reserva ativa, mas o total físico permanece inalterado
        assertThat(stock.getTotalQuantity()).isEqualTo(10);
        assertThat(stock.getQuantityAvailable()).isEqualTo(6);
        assertThat(stock.getReservations()).contains(reservation);
    }

    @Test
    @DisplayName("Should throw an exception when attempting to reserve a quantity greater than what is available in stock")
    void shouldThrowExceptionWhenReservingMoreThanAvailable() {
        Stock stock = new Stock(new StockId(), new ProductId("prod-123"), 10);

        assertThatThrownBy(() -> stock.reservation("cart-1", "cust-1", 11))
                .isInstanceOf(IllegalReservationException.class)
                .hasMessageContaining("Insufficient stock");
    }

    @Test
    @DisplayName("Should restore available stock when cancelling an active reservation")    void shouldCancelReservationAndRecalculateStock() {
        Stock stock = new Stock(new StockId(), new ProductId("prod-123"), 50);
        
        // Cria reserva de 10 itens -> Sobram 40 disponíveis
        Reservations res = stock.reservation("cart-1", "cust-1", 10);
        String reservationId = res.getReservationId();
        assertThat(stock.getQuantityAvailable()).isEqualTo(40);

        stock.cancelReservation(reservationId);

        // Reserva deve ir para CANCELED e os 10 itens retornam ao disponível
        assertThat(res.getReserveStatus()).isEqualTo(ReserveStatus.CANCELED);
        assertThat(stock.getQuantityAvailable()).isEqualTo(50);
    }

    @Test
    @DisplayName("Should throw an exception when attempting to cancel a reservation that is already cancelled")    void shouldThrowExceptionWhenCancelingAlreadyCanceledReservation() {
        Stock stock = new Stock(new StockId(), new ProductId("prod-123"), 20);
        Reservations res = stock.reservation("cart-1", "cust-1", 5);
        String reservationId = res.getReservationId();

        stock.cancelReservation(reservationId); // Primeiro cancelamento: OK

        assertThatThrownBy(() -> stock.cancelReservation(reservationId))
                .isInstanceOf(IllegalReservationException.class)
                .hasMessageContaining("This reservation was previously cancelled");
    }

    @Test
    @DisplayName("You must definitively write off physical stock (total) and record product issue when confirming order")
    void shouldConfirmOrderAndGenerateProductOutput() {
        Stock stock = new Stock(new StockId(), new ProductId("prod-123"), 30);
        Reservations res = stock.reservation("cart-1", "cust-1", 10);
        String reservationId = res.getReservationId();

        stock.confirmOrder("prod-123", reservationId);

        // Confirmado -> A reserva vira CONSUMED, deduz 10 unidades físicas do total permanentemente
        assertThat(res.getReserveStatus()).isEqualTo(ReserveStatus.CONSUMED);
        assertThat(stock.getTotalQuantity()).isEqualTo(20);
        assertThat(stock.getQuantityAvailable()).isEqualTo(20); // Consumido não filtra como ativo, o cálculo zera o impacto da reserva mas deduz do total físico

        // Valida que gerou a movimentação de saída correspondente
        assertThat(stock.getProductOutput()).hasSize(1);
        ProductOutput output = stock.getProductOutput().get(0);
        assertThat(output.getProductId()).isEqualTo(new ProductId("prod-123"));
        assertThat(output.getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should throw an exception when retrieving a reservation by a non-existent ID")
    void shouldThrowExceptionWhenReservationIdNotFound() {
        Stock stock = new Stock("prod-123");

        assertThatThrownBy(() -> stock.getReservationId("id-fantasma"))
                .isInstanceOf(IllegalReservationException.class)
                .hasMessageContaining("Reservation not found");
    }

    @Test
    @DisplayName("Should allow batch inclusion of lists for data migration or persistence")
    void shouldAllowBulkInsertOfLists() {
        Stock stock = new Stock("prod-123");
        
        Reservations resMock = new Reservations("res-1", "cart-1", "cust-1", 5, LocalDateTime.now(), ReserveStatus.ACTIVE);
        ProductInput inputMock = mock(ProductInput.class);
        ProductOutput outputMock = mock(ProductOutput.class);

        stock.addReservations(List.of(resMock));
        stock.addProductInput(List.of(inputMock));
        stock.addProductOutput(List.of(outputMock));

        assertThat(stock.getReservations()).hasSize(1);
        assertThat(stock.getProductInput()).hasSize(1);
        assertThat(stock.getProductOutput()).hasSize(1);
        // O total do estoque era 0. Como injetamos uma reserva de 5 ativa sem aumentar o total físico, a quantidade disponível fica negativa (-5) de acordo com a matemática de recalcularQuantityAvailable()
        assertThat(stock.getQuantityAvailable()).isEqualTo(-5);
    }

    @Test
    @DisplayName("Must respect the equality contract (equals and hashCode) based on StockId")
    void shouldRespectEqualsAndHashCodeContractBasedOnId() {
        StockId identicalId = new StockId();

        Stock s1 = new Stock(identicalId, new ProductId("prod-1"), 10);
        Stock s2 = new Stock(identicalId, new ProductId("prod-2"), 999); // ID idêntico, outros atributos diferentes
        Stock sDifferent = new Stock(new StockId(), new ProductId("prod-1"), 10);

        assertThat(s1).isEqualTo(s2);
        assertThat(s1.hashCode()).isEqualTo(s2.hashCode());

        assertThat(s1).isNotEqualTo(sDifferent);
        assertThat(s1).isNotEqualTo(null);
    }
}