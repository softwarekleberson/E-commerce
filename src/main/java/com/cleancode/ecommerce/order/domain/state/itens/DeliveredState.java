package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class DeliveredState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.DELIVERED;
    }
    
    @Override
    public void cancelled(OrderItem item) {
        throw new IllegalDomainOrder("A delivered item cannot be cancelled.");
    }

    @Override
    public void ship(OrderItem item) {
        throw new IllegalDomainOrder("A delivered item cannot be shipped.");
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("Item is already delivered.");
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("A delivered item cannot be in separating state.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("A delivered item cannot return to awaiting payment.");
    }
}