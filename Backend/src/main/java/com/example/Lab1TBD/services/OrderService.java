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

    public OrderEntity getOrdersByOrderId(Long order_id){
        return orderRepository.findByOrderId(order_id);
    }

    public List<OrderEntity> getAllOrders(){
        return orderRepository.findAllOrders();
    }

    public List<OrderEntity> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<OrderEntity> getOrdersByStatus(String status){
        return orderRepository.findByStatus(status);
    }

    public Long saveOrder(OrderEntity order) {
        return orderRepository.saveOrder(order); // Retorna el ID generado
    }

    public void updateOrder(OrderEntity order){
        orderRepository.updateOrder(order);
    }

    public void deleteOrderById(Long order_id){
        orderRepository.deleteOrderById(order_id);
    }
}