package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.OrderEntity;
import com.example.Lab1TBD.persistence.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderEntity> getOrdersByClientId(long clientId) {
        return orderRepository.findByClientId(clientId);
    }
}
