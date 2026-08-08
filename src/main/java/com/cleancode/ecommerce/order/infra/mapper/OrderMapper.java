package com.cleancode.ecommerce.order.infra.mapper;

import java.util.stream.Collectors;

import com.cleancode.ecommerce.cart.infra.persistence.TypeCoinEntity;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.infra.persistencia.OrderEntity;
import com.cleancode.ecommerce.order.infra.persistencia.OrderStatusEntity;
import com.cleancode.ecommerce.payment.infra.mapper.OrderItensMapper;

public class OrderMapper {

	public static Order toDomain(OrderEntity entity) {
		if(entity == null) {
			return null;
		}
		
		Order order = new Order(entity.getCustomer_Id(), entity.getDelivery_Id());
		
		if(entity.getOrder_itens() != null && !entity.getOrder_itens().isEmpty()) {
			order.addOrderItem(
					entity.getOrder_itens().stream().map(OrderItensMapper::toDomain).collect(Collectors.toList())
			);
		}
		return order;
	}
	
	public static OrderEntity toEntity(Order order) {
		if(order == null) {
			return null;
		}
		
		OrderEntity entity = new OrderEntity();
		entity.setOrder_Id(order.getOrderId().getOrderId());
		entity.setCustomer_Id(order.getCustomerId().getValue());
		entity.setDelivery_Id(order.getDeliveryId());
		entity.setCreated_At(order.getCreatedAt());
		entity.setValue(order.getTotal().getTotalValue());
		entity.setType_Coin(TypeCoinEntity.valueOf(order.getTotal().getTypeCoin().name()));
		entity.setStatus(OrderStatusEntity.valueOf(order.getOrderStatus().name()));
		
		if(order.getItems() != null && !order.getItems().isEmpty()) {
			entity.setOrder_itens(order.getItems().stream().map(o -> OrderItensMapper.toEntity(o, entity))
					.collect(Collectors.toList()));
		}
		
		return entity;
	}
}
