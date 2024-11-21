package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;
import com.example.Lab1TBD.persistence.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderDetailEntity getOrderDetailById(long id) {
        return orderDetailRepository.findOrderDetailById(id);
    }

    public void saveOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailRepository.saveOrderDetail(orderDetail);
    }

    public void updateOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailRepository.alterOrderDetailById(orderDetail);
    }

    public void deleteOrderDetail(long id) {
        orderDetailRepository.deleteOrderDetailById(id);
    }
}
