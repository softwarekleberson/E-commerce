package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class ExchangeRejectedState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.EXCHANGE_REJECTED;
    }

    @Override
    public void cancelled(OrderItem item) {
        throw new IllegalDomainOrder("Cannot cancel an item with a rejected exchange.");
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("Cannot place an item with a rejected exchange in separating state.");
    }

    @Override
    public void ship(OrderItem item) {
        throw new IllegalDomainOrder("Cannot ship an item with a rejected exchange.");
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("Cannot deliver an item with a rejected exchange.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("Item with a rejected exchange cannot return to awaiting payment.");
    }

    @Override
    public void exchangeRequest(OrderItem item) {
        throw new IllegalDomainOrder("Cannot request a new exchange for an item with a rejected exchange.");
    }

    @Override
    public void exchangeAccepted(OrderItem item) {
        throw new IllegalDomainOrder("Cannot accept an exchange that has already been rejected.");
    }

    @Override
    public void exchangeRejected(OrderItem item) {
        throw new IllegalDomainOrder("Exchange is already rejected for this item.");
    }
}