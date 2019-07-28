package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);

    List<Product> findProducts(String query, String sort, String order);

    void save(Product product);

    void delete(Long id);
}
