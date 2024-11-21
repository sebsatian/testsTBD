package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;

public interface OrderDetailRepository {
    OrderDetailEntity findOrderDetailById(long order_detail_id);
    OrderDetailEntity findOrderDetailByPrice(float price);
    OrderDetailEntity findOrderDetailByQuantity(int quantity);
    OrderDetailEntity findOrderDetailByOrderId(long order_id);
    OrderDetailEntity findOrderDetailByProductId(long product_id);
    void saveOrderDetail(OrderDetailEntity orderDetail);
    void alterOrderDetailById(OrderDetailEntity orderDetail);
    void deleteOrderDetailById(long order_detail_id);

}