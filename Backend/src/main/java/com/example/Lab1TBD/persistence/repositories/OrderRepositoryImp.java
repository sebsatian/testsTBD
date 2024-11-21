package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class OrderRepositoryImp implements OrderRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<OrderEntity> findByClientId(Long clientId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE client_id = :client_id")
                    .addParameter("client_id", clientId)
                    .executeAndFetch(OrderEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderEntity findByOrderId(Long order_id){
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM order WHERE order_id = :order_id")
                    .addParameter("order_id",order_id)
                    .executeAndFetchFirst(OrderEntity.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
