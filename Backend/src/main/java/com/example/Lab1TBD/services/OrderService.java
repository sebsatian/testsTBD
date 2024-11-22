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

    public OrderEntity getOrdersByOrderId(long order_id){
        return orderRepository.findByOrderId(order_id);
    }

    public List<OrderEntity> getAllOrders(){
        return orderRepository.findAllOrders();
    }

    public List<OrderEntity> getOrdersByClientId(long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<OrderEntity> getOrdersByStatus(String status){
        return orderRepository.findByStatus(status);
    }

    public void saveOrder(OrderEntity order){
        orderRepository.saveOrder(order);
    }

    public void updateOrder(OrderEntity order){
        orderRepository.updateOrder(order);
    }

    public void deleteOrderById(long order_id){
        orderRepository.deleteOrderById(order_id);
    }
}