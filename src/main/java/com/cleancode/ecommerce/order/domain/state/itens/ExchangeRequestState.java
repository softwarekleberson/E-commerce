package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class ExchangeRequestState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.EXCHANGE_REQUEST; 
    }

    @Override
    public void cancelled(OrderItem item) {
        throw new IllegalDomainOrder("Cannot cancel an item with a pending exchange request.");
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("Cannot place an item with a pending exchange request in separating state.");
    }

    @Override
    public void ship(OrderItem item) {
        throw new IllegalDomainOrder("Cannot ship an item with a pending exchange request.");
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("Cannot deliver an item with a pending exchange request.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("Item with a pending exchange request cannot return to awaiting payment.");
    }

    @Override
    public void exchangeRequest(OrderItem item) {
        throw new IllegalDomainOrder("Exchange request is already pending for this item.");
    }

    @Override
    public void exchangeAccepted(OrderItem item) {
        item.setItemState(new ExchangeAcceptedState());
    }

    @Override
    public void exchangeRejected(OrderItem item) {
        item.setItemState(new ExchangeRejectedState());
    }
}