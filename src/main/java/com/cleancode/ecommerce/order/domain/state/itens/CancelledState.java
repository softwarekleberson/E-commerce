package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class CancelledState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.CANCELLED;
    }
    
    @Override
    public void cancelled(OrderItem item) {
        throw new IllegalDomainOrder("Item is already cancelled.");
    }

    @Override
    public void ship(OrderItem item) {
        throw new IllegalDomainOrder("Item cannot be in a shipped state when cancelled.");
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("Item cannot be in a delivered state when cancelled.");
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("Item cannot be in a separating state when cancelled.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("Item cannot return to awaiting payment when cancelled.");
    }
}