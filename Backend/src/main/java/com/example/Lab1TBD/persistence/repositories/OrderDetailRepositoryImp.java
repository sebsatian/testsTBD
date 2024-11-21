package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class OrderDetailRepositoryImp implements OrderDetailRepository {

    @Autowired
    private Sql2o sql2o;

    // Buscar detalle de orden por ID
    @Override
    public OrderDetailEntity findOrderDetailById(long order_detail_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_detail WHERE id = :order_detail_id")
                    .addParameter("order_detail_id", order_detail_id)
                    .executeAndFetchFirst(OrderDetailEntity.class); // Cambiado para obtener un único resultado
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo de errores
        }
    }

    // Buscar detalle de orden por precio
    @Override
    public OrderDetailEntity findOrderDetailByPrice(float price) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_detail WHERE price = :price")
                    .addParameter("price", price)
                    .executeAndFetchFirst(OrderDetailEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar detalle de orden por cantidad (stock)
    @Override
    public OrderDetailEntity findOrderDetailByQuantity(int quantity) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_detail WHERE stock = :quantity")
                    .addParameter("quantity", quantity)
                    .executeAndFetchFirst(OrderDetailEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar detalle de orden por ID de orden
    @Override
    public OrderDetailEntity findOrderDetailByOrderId(long order_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_detail WHERE order_id = :order_id")
                    .addParameter("order_id", order_id)
                    .executeAndFetchFirst(OrderDetailEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar detalle de orden por ID de producto
    @Override
    public OrderDetailEntity findOrderDetailByProductId(long product_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_detail WHERE product_id = :product_id")
                    .addParameter("product_id", product_id)
                    .executeAndFetchFirst(OrderDetailEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Guardar un nuevo detalle de orden
    @Override
    public void saveOrderDetail(OrderDetailEntity orderDetail) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO order_detail (stock, price, order_id, product_id) " +
                            "VALUES (:quantity, :price, :order_id, :product_id)")
                    .addParameter("quantity", orderDetail.getQuantity())
                    .addParameter("price", orderDetail.getPrice())
                    .addParameter("order_id", orderDetail.getOrder_id())
                    .addParameter("product_id", orderDetail.getProduct_id())
                    .executeUpdate();
            con.commit(); // Confirmar la transacción
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar un detalle de orden por ID
    @Override
    public void alterOrderDetailById(OrderDetailEntity orderDetail) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE order_detail " +
                            "SET stock = :quantity, price = :price, order_id = :order_id, product_id = :product_id " +
                            "WHERE id = :order_detail_id")
                    .addParameter("quantity", orderDetail.getQuantity())
                    .addParameter("price", orderDetail.getPrice())
                    .addParameter("order_id", orderDetail.getOrder_id())
                    .addParameter("product_id", orderDetail.getProduct_id())
                    .addParameter("order_detail_id", orderDetail.getOrder_detail_id())
                    .executeUpdate();
            con.commit(); // Confirmar la transacción
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrar un detalle de orden por ID
    @Override
    public void deleteOrderDetailById(long order_detail_id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM order_detail WHERE id = :order_detail_id")
                    .addParameter("order_detail_id", order_detail_id)
                    .executeUpdate();
            con.commit(); // Confirmar la transacción
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
