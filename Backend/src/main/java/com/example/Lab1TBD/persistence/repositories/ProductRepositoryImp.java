package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    @Autowired
    private Sql2o sql2o;

    // Buscar producto por ID
    @Override
    public ProductEntity findProductById(long product_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE product_id = :product_id")
                    .addParameter("product_id", product_id)
                    .executeAndFetchFirst(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductEntity> findAllProducts(){
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM product")
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar producto por nombre
    @Override
    public List<ProductEntity> findProductByName(String product_name) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE product_name LIKE :product_name")
                    .addParameter("product_name", "%" + product_name + "%")
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar productos por estado
    @Override
    public List<ProductEntity> findProductsByStatus(String product_status) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE product_status = :product_status")
                    .addParameter("product_status", product_status)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar productos por stock
    @Override
    public List<ProductEntity> findProductByStock(int stock) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE stock = :stock")
                    .addParameter("stock", stock)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Buscar productos por descripción
    @Override
    public List<ProductEntity> findProductByDescription(String description) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE description ILIKE :description")
                    .addParameter("description", "%" + description + "%") // Búsqueda parcial
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Buscar productos por precio
    @Override
    public List<ProductEntity> findProductByPrice(float price) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE price = :price")
                    .addParameter("price", price)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar productos por categoría
    @Override
    public List<ProductEntity> findProductsByCategoryId(long category_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE category_id = :category_id")
                    .addParameter("category_id", category_id)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Guardar un nuevo producto
    @Override
    public void saveProduct(ProductEntity product) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO product (product_name, description, price, stock, product_status, category_id) " +
                            "VALUES (:product_name, :description, :price, :stock, :product_status, :category_id)")
                    .addParameter("product_name", product.getProduct_name())
                    .addParameter("description", product.getDescription())
                    .addParameter("price", product.getPrice())
                    .addParameter("stock", product.getStock())
                    .addParameter("product_status", product.getProduct_status())
                    .addParameter("category_id", product.getCategory_id())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar un producto por ID
    @Override
    public void updateProduct(ProductEntity product) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE product " +
                            "SET product_name = :product_name, description = :description, price = :price, " +
                            "stock = :stock, product_status = :product_status, category_id = :category_id " +
                            "WHERE product_id = :product_id")
                    .addParameter("product_name", product.getProduct_name())
                    .addParameter("description", product.getDescription())
                    .addParameter("price", product.getPrice())
                    .addParameter("stock", product.getStock())
                    .addParameter("product_status", product.getProduct_status())
                    .addParameter("category_id", product.getCategory_id())
                    .addParameter("product_id", product.getProduct_id())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrar un producto por ID
    @Override
    public void deleteProductById(long product_id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM product WHERE product_id = :product_id")
                    .addParameter("product_id", product_id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            // Registrar el error en lugar de solo imprimir
            throw new RuntimeException("Error al eliminar el producto con ID " + product_id, e);
        }
    }

}