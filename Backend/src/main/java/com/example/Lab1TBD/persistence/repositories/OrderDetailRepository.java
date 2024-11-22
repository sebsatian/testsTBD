package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;

import java.util.List;

public interface OrderDetailRepository {
    List<OrderDetailEntity> findAllOrderDetail();
    OrderDetailEntity findOrderDetailById(long order_detail_id);
    OrderDetailEntity findOrderDetailByPrice(float price);
    OrderDetailEntity findOrderDetailByQuantity(int quantity);
    OrderDetailEntity findOrderDetailByOrderId(long order_id);
    OrderDetailEntity findOrderDetailByProductId(long product_id);

    void saveOrderDetail(OrderDetailEntity orderDetail);
    void updateOrderDetail(OrderDetailEntity orderDetail);
    void deleteOrderDetailById(long order_detail_id);

}