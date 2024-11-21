package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderEntity;

import java.util.List;

public interface OrderRepository {
    List<OrderEntity> findByClientId(Long clientId);
    OrderEntity findByOrderId(Long order_id);
}
