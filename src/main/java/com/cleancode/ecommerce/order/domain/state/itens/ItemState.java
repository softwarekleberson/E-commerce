package com.cleancode.ecommerce.order.domain.state.itens;

import com.cleancode.ecommerce.order.domain.OrderItem;

public interface ItemState {

    void cancelled(OrderItem item);
    
    void separating (OrderItem item);
        
    void ship(OrderItem item);
    
    void delivered(OrderItem item);
    
    void awaitingPayment (OrderItem item);
    
    void exchangeRequest (OrderItem item);
    
    void exchangeAccepted (OrderItem item);
    
    void exchangeRejected (OrderItem item);
    
    ItemStatus getItemState();
}
