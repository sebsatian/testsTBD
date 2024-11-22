package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;

import java.util.List;

public interface OrderDetailRepository {
    List<OrderDetailEntity> findAllOrderDetail();
    OrderDetailEntity findOrderDetailById(Long order_detail_id);
    OrderDetailEntity findOrderDetailByPrice(float price);
    OrderDetailEntity findOrderDetailByQuantity(int quantity);
    OrderDetailEntity findOrderDetailByOrderId(Long order_id);
    OrderDetailEntity findOrderDetailByProductId(Long product_id);

    void saveOrderDetail(OrderDetailEntity orderDetail);
    void updateOrderDetail(OrderDetailEntity orderDetail);
    void deleteOrderDetailById(Long order_detail_id);

}