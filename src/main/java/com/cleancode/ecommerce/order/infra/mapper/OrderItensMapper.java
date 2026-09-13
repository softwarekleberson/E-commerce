package com.cleancode.ecommerce.order.infra.mapper;

import com.cleancode.ecommerce.order.domain.OrderItem;
import com.cleancode.ecommerce.order.domain.state.itens.ItemStatus;
import com.cleancode.ecommerce.order.infra.persistencia.ItemStatusEntity;
import com.cleancode.ecommerce.order.infra.persistencia.OrderEntity;
import com.cleancode.ecommerce.order.infra.persistencia.OrderItemEntity;

public class OrderItensMapper {

	public static OrderItem toDomain(OrderItemEntity entity) {
		return new OrderItem
		(entity.getProduct_id(),
		 entity.getPrice(),
		 entity.getQuantity(),
		 entity.getReservation_id(),
		 ItemStatus.valueOf(entity.getItemStatus().name()));
	}
	
	public static OrderItemEntity toEntity(OrderItem domain, OrderEntity orderEntity) {
		OrderItemEntity entity = new OrderItemEntity();
		entity.setProduct_id(domain.getProductId());
		entity.setPrice(domain.getPrice());
		entity.setQuantity(domain.getQuantity());
		entity.setSubtotal(domain.getSubtotal());
		entity.setReservation_id(domain.getStockOutId());
		
		entity.setItemStatus(ItemStatusEntity.valueOf(domain.getItemStatus().name()));
		
		entity.setOrder(orderEntity);
		return entity;
	}
}