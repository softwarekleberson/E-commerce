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
        throw new IllegalDomainOrder("The item is already in transit.");
    }

    @Override
    public void delivered(OrderItem item) {
        item.setItemState(new DeliveredState());
    }

    @Override
    public void separating(OrderItem item) {
        throw new IllegalDomainOrder("The item is already in transit; it cannot return to the separating stage.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("The item is already in transit; it cannot return to awaiting payment.");
    }
}