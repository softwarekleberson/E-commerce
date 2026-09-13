package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class ExchangeAcceptedState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.EXCHANGE_ACCEPTED;
    }

    @Override
    public void cancelled(OrderItem item) {
        throw new IllegalDomainOrder("Cancellation unavailable: exchange already accepted.");
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("Separation unavailable: exchange already accepted.");
    }

    @Override
    public void ship(OrderItem item) {
        throw new IllegalDomainOrder("Shipment unavailable: exchange already accepted.");
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("Delivery unavailable: exchange already accepted.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("Payment status unavailable: exchange already accepted.");
    }

    @Override
    public void exchangeRequest(OrderItem item) {
        throw new IllegalDomainOrder("Exchange request unavailable: exchange already accepted.");
    }

    @Override
    public void exchangeAccepted(OrderItem item) {
        throw new IllegalDomainOrder("Exchange accept unavailable: already in accepted state.");
    }

    @Override
    public void exchangeRejected(OrderItem item) {
        throw new IllegalDomainOrder("Exchange reject unavailable: exchange already accepted.");
    }
}