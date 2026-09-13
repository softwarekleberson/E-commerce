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

    @Override
    public void exchangeRequest(OrderItem item) {
        throw new IllegalDomainOrder("Cannot request exchange for a cancelled item.");
    }

    @Override
    public void exchangeAccepted(OrderItem item) {
        throw new IllegalDomainOrder("Cannot accept exchange for a cancelled item.");
    }

    @Override
    public void exchangeRejected(OrderItem item) {
        throw new IllegalDomainOrder("Cannot reject exchange for a cancelled item.");
    }
}