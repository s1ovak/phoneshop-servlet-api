package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private ArrayListProductDao() {
    }

    private List<Product> productList = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) {
        Objects.requireNonNull(id, "ID should not be null");
        Product product = this.productList.stream()
                .filter(product1 -> product1.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with such ID = " + id + " is not found"));
        return deepCopyProduct(product);
    }

    @Override
    public synchronized List<Product> searchValidProducts(String query, Comparator<Product> productComparator) {
        List<Product> products = findProducts();
        products = findProducts(query, products);
        products = sortProducts(productComparator, products);

        return products;
    }

    @Override
    public synchronized void save(Product product) {
        Objects.requireNonNull(product, "Product to save should not be null");
        Optional<Product> validProduct = this.productList.stream()
                .filter(product1 -> product1.getId().equals(product.getId()))
                .findFirst();
        if (validProduct.isPresent()) {
            productList.set(productList.indexOf(validProduct.get()), deepCopyProduct(product));
        } else {
            this.productList.add(deepCopyProduct(product));
        }
    }

    @Override
    public synchronized void delete(Long id) {
        Objects.requireNonNull(id, "ID should not be null");

        this.productList.stream().filter(product -> product.getId().equals(id))
                .findFirst()
                .map(product -> this.productList.remove(product))
                .orElseThrow(() -> new ProductNotFoundException("Product with such ID = " + id + " is not found"));
    }

    @Override
    public synchronized void clearAll() {
        this.productList.clear();
    }

    @Override
    public List<Product> findProducts(String query, List<Product> validProducts) {
        Objects.requireNonNull(validProducts, "Collection should not be null");
        if (query != null && !query.trim().isEmpty()) {
            String[] queries = query.toLowerCase().split(" ");

            Map<Product, Integer> map = new HashMap<>();
            validProducts.forEach(product -> {
                Integer number = 0;
                for (String q : queries) {
                    if (product.getDescription().toLowerCase().contains(q)) {
                        number++;
                    }
                }

                if (number > 0) {
                    map.put(product, number);
                }
            });

            return map.entrySet().stream()
                    .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                    .map(Map.Entry::getKey).collect(Collectors.toList());
        } else {
            return validProducts;
        }
    }

    @Override
    public List<Product> findProducts() {
        return this.productList.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .map(product -> product = deepCopyProduct(product))
                .collect(Collectors.toList());
    }

    private List<Product> sortProducts(
            Comparator<Product> productComparator, List<Product> products) {
        Objects.requireNonNull(products, "Collection should not be null");

        if (productComparator != null) {
            products = products
                    .stream()
                    .sorted(productComparator)
                    .collect(Collectors.toList());
        }
        return products;
    }

    private Product deepCopyProduct(Product product) {
        return new Product(product);
    }
}
