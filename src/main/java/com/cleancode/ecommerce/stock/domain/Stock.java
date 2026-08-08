package com.cleancode.ecommerce.stock.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.shared.kernel.Price;
import com.cleancode.ecommerce.stock.domain.exception.IllegalReservationException;
import com.cleancode.ecommerce.stock.domain.productinput.ProductInput;
import com.cleancode.ecommerce.stock.domain.productoutput.ProductOutput;
import com.cleancode.ecommerce.stock.domain.reservation.Reservations;
import com.cleancode.ecommerce.stock.domain.reservation.ReserveStatus;

public class Stock {

	public static final int MIN_QUANTITY = 0;

	private StockId stockId;
	private ProductId productId;
	private int totalQuantity;
	private int quantityAvailable;
	private List<Reservations> reservations = new ArrayList<>();
	private List<ProductInput> productInputs = new ArrayList<>();
	private List<ProductOutput> productOutputs = new ArrayList<>();

	public Stock(String productId) {
		this.stockId = new StockId();
		this.productId = new ProductId(productId);
	}

	public Stock(StockId id, ProductId productId, int totalQuantity) {
		this.stockId = id;
		this.productId = productId;
		this.totalQuantity = totalQuantity;
		this.quantityAvailable = totalQuantity;
	}

	public void addReservations(List<Reservations> reservations) {
		this.reservations.addAll(reservations);
		recalculateQuantityAvailable();
	}

	public void addProductInput(List<ProductInput> productInput) {
		this.productInputs.addAll(productInput);
	}

	public void addProductOutput(List<ProductOutput> productOutput) {
		this.productOutputs.addAll(productOutput);
	}

	public void addProductInput(int quantity, ProductQuality productQuality, Price purchasePrice, String supplier) {
		if (quantity <= MIN_QUANTITY) {
			throw new IllegalDomainException("Quantity must be positive");
		}

		this.totalQuantity += quantity;
		ProductInput entryMovement = new ProductInput(quantity, productQuality, purchasePrice, supplier);
		this.productInputs.add(entryMovement);

		recalculateQuantityAvailable();
	}

	public Reservations reservation(String cartId, String customerId, int quantity) {
		if (quantity > this.quantityAvailable || quantity <= 0) {
			throw new IllegalReservationException("Insufficient stock");
		}

		Reservations reservation = new Reservations(cartId, customerId, quantity);
		this.reservations.add(reservation);

		recalculateQuantityAvailable();
		return reservation;
	}

	public void cancelReservation(String reservationId) {
		Reservations reservation = getReservationId(reservationId);
		System.out.println(reservationId);
		if (reservation.getReserveStatus() == ReserveStatus.CANCELED) {
			System.out.println("Bati aqui");
			throw new IllegalReservationException("This reservation was previously cancelled");
		}

		reservation.cancel();
		recalculateQuantityAvailable();
	}

	public void confirmOrder(String productId, String reservationId) {
		Reservations reservation = getReservationId(reservationId);
		reservation.confirmOrder();

		this.totalQuantity -= reservation.getQuantity();
		this.productOutputs
				.add(new ProductOutput(new ProductId(productId), reservation.getQuantity()));

		recalculateQuantityAvailable();
	}

	private void recalculateQuantityAvailable() {
		int totalReserved = this.reservations.stream().filter(r -> r.getReserveStatus() == ReserveStatus.ACTIVE)
				.mapToInt(r -> r.getQuantity()).sum();

		this.quantityAvailable = this.totalQuantity - totalReserved;
	}
	
	public Reservations getReservationId(String reservationId) {
		return reservations.stream().filter(r -> r.getReservationId().equals(reservationId)).findFirst()
				.orElseThrow(() -> new IllegalReservationException("Reservation not found"));
	}

	public StockId getStockId() {
		return stockId;
	}

	public ProductId getProductId() {
		return productId;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public List<Reservations> getReservations() {
		return Collections.unmodifiableList(this.reservations);
	}

	public List<ProductInput> getProductInput() {
		return Collections.unmodifiableList(this.productInputs);
	}

	public List<ProductOutput> getProductOutput() {
		return Collections.unmodifiableList(this.productOutputs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(stockId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(stockId, other.stockId);
	}

	@Override
	public String toString() {
		return "Stock [id=" + stockId + ", productId=" + productId + ", totalQuantity=" + totalQuantity
				+ ", quantityAvailable=" + quantityAvailable + ", reservations=" + reservations + ", productInputs="
				+ productInputs + ", productOutputs=" + productOutputs + "]";
	}
}
