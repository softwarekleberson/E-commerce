package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class ShippedState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.SHIPPED;
    }

    @Override
    public void cancelled(OrderItem item) {
        item.setItemState(new CancelledState());
    }

    @Override
    public void ship(OrderItem item) {
        throw new IllegalDomainOrder("Item is already shipped.");
    }

    @Override
    public void delivered(OrderItem item) {
        item.setItemState(new DeliveredState());
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("Cannot return a shipped item to separating state.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("Cannot return a shipped item to awaiting payment.");
    }

    @Override
    public void exchangeRequest(OrderItem item) {
        throw new IllegalDomainOrder("Cannot request exchange for a shipped item before delivery.");
    }

    @Override
    public void exchangeAccepted(OrderItem item) {
        throw new IllegalDomainOrder("Cannot accept exchange for a shipped item.");
    }

    @Override
    public void exchangeRejected(OrderItem item) {
        throw new IllegalDomainOrder("Cannot reject exchange for a shipped item.");
    }
}