package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> productList = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Parameter 'id' should not be null");

        return this.productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product with such ID = " + id + "is not found"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return this.productList.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (this.productList.stream()
                .noneMatch(product1 -> product1.getId().equals(product.getId())
                        && product1.getCode().equals(product.getCode())
                        && product1.getDescription().equals(product.getDescription())
                )) {
            this.productList.add(product);
        } else {
            throw new IllegalArgumentException("Product with such id/code/description is already exist.");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        this.productList.stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .map(product -> this.productList.remove(product))
                .orElseThrow(() -> new NoSuchElementException("Product with such ID is not found"));
    }
}
