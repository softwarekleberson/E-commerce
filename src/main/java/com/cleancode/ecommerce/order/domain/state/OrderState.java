package com.cleancode.ecommerce.order.domain.state;

import com.cleancode.ecommerce.order.domain.OrderStatus;
import com.cleancode.ecommerce.order.domain.Order;

public interface OrderState {

	void pay(Order order);

    void cancel(Order order);
    
    void pending (Order order);
    
    void ship(Order order);
    
    void delivered(Order order);
    
	OrderStatus getOrderStatus();
}
