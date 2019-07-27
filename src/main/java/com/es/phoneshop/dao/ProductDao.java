package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;

import java.util.Comparator;
import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> searchValidProducts(String query, Comparator<Product> productComparator);

    void save(Product product);

    void delete(Long id);

    void clearAll();
}
