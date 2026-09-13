package com.cleancode.ecommerce.order.domain.state.order;

import com.cleancode.ecommerce.order.domain.Order;

public interface OrderState {

	void pay(Order order);
    
    void pending (Order order);
        
	OrderStatus getOrderStatus();
}
