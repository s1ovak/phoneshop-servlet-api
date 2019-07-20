package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> productList = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) {
        Objects.requireNonNull(id, "ID should not be null");
        Product product = this.productList.stream()
                .filter(product1 -> product1.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Product with such ID = " + id + "is not found"));
        return new Product(product);
    }

    @Override
    public synchronized List<Product> findProducts() {
        List<Product> buf = this.productList.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
        buf.forEach(product -> product = new Product(product));
        return buf;
    }

    @Override
    public synchronized void save(Product product) {
        Objects.requireNonNull(product, "Product to save should not be null");
        Optional<Product> buf = this.productList.stream()
                .filter(product1 -> product1.getId().equals(product.getId()))
                .findFirst();
        if (buf.isPresent()) {
            productList.set(productList.indexOf(buf.get()), product);
        } else {
            this.productList.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        Objects.requireNonNull(id, "ID should not be null");

        this.productList.stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .map(product -> this.productList.remove(product))
                .orElseThrow(() -> new NoSuchElementException("Product with such ID = " + id + "is not found"));
    }
}
