package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderEntity;

import java.util.List;

public interface OrderRepository {
    OrderEntity findByOrderId(long order_id);
    List<OrderEntity> findAllOrders();
    List<OrderEntity> findByClientId(long clientId);
    List<OrderEntity> findByStatus(String status);

    void saveOrder(OrderEntity order);
    void updateOrder(OrderEntity order);
    void deleteOrderById(long order_id);
}
