package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class SeparatingState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.SEPARATING;
    }

    @Override
    public void cancelled(OrderItem item) {
        item.setItemState(new CancelledState());
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("Item is already being separated.");
    }

    @Override
    public void ship(OrderItem item) {
        item.setItemState(new ShippedState());
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("Cannot deliver an item directly while in separation.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("Cannot return an item in separation to awaiting payment.");
    }

    @Override
    public void exchangeRequest(OrderItem item) {
        throw new IllegalDomainOrder("Cannot request exchange for an item in separation.");
    }

    @Override
    public void exchangeAccepted(OrderItem item) {
        throw new IllegalDomainOrder("Cannot accept exchange for an item in separation.");
    }

    @Override
    public void exchangeRejected(OrderItem item) {
        throw new IllegalDomainOrder("Cannot reject exchange for an item in separation.");
    }
}