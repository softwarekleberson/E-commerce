package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;

public class AwaitingPaymentState implements ItemState {

    @Override
    public ItemStatus getItemState() {
        return ItemStatus.AWAITING_PAYMENT;
    }
    
    @Override
    public void cancelled(OrderItem item) {
        item.setItemState(new CancelledState());
    }

    @Override
    public void separating(OrderItem item) {
        item.setItemState(new SeparatingState());
    }

    @Override
    public void ship(OrderItem item) {
        throw new IllegalDomainOrder("The item cannot be shipped because payment has not been confirmed.");
    }

    @Override
    public void delivered(OrderItem item) {
        throw new IllegalDomainOrder("The item cannot be delivered because payment has not been confirmed.");
    }

    @Override
    public void awaitingPayment(OrderItem item) {
        throw new IllegalDomainOrder("The item is already awaiting payment.");
    }
}