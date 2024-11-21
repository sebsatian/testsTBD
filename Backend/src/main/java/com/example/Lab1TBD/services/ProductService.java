package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.ProductEntity;
import com.example.Lab1TBD.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductEntity getProductById(long id) {
        return productRepository.findProductById(id);
    }

    public List<ProductEntity> getProductsByName(String name) {
        return productRepository.findProductByName(name);
    }

    public List<ProductEntity> getProductsByStatus(String status) {
        return productRepository.findProductsByStatus(status);
    }

    public List<ProductEntity> getProductsByCategory(long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }

    public void saveProduct(ProductEntity product) {
        productRepository.saveProduct(product);
    }

    public void updateProduct(ProductEntity product) {
        productRepository.updateProductById(product);
    }

    public void deleteProduct(long id) {
        productRepository.deleteProductById(id);
    }
}
