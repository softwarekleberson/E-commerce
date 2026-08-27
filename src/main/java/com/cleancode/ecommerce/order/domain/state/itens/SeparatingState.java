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
        throw new IllegalDomainOrder("The item is already being separated.");
    }

    @Override
    public void ship(OrderItem item) {
        item.setItemState(new SeparatingState());
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("The item is being separated and cannot be delivered directly.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("The item is being separated and cannot return to awaiting payment.");
    }
}