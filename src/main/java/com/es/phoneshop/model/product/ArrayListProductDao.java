package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;

    private ArrayListProductDao() {
    }

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
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
        return new Product(product);
    }

    @Override
    public synchronized List<Product> findProducts(String query, String sort, String order) {
        List<Product> validProducts = findValidProducts();

        if (query != null && !query.trim().isEmpty()) {
            validProducts = findProductsByQueryParam(query.toLowerCase().split(" "), validProducts);
        }

        if (sort != null && !sort.trim().isEmpty() && order != null && !order.trim().isEmpty()) {
            validProducts = sortProducts(sort, order, validProducts);
        }

        return validProducts;
    }

    @Override
    public synchronized void save(Product product) {
        Objects.requireNonNull(product, "Product to save should not be null");
        Optional<Product> validProducts = this.productList.stream()
                .filter(product1 -> product1.getId().equals(product.getId()))
                .findFirst();
        if (validProducts.isPresent()) {
            productList.set(productList.indexOf(validProducts.get()), product);
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
                .orElseThrow(() -> new ProductNotFoundException("Product with such ID = " + id + " is not found"));
    }

    private List<Product> findProductsByQueryParam(String[] query, List<Product> validProducts) {
        Objects.requireNonNull(validProducts, "Collection should not be null");
        HashMap<Product, Integer> map = new HashMap<>();
        validProducts.forEach(product -> {
            Integer number = 0;
            for (String q : query) {
                if (product.getDescription().toLowerCase().contains(q)) {
                    number++;
                }
            }

            if (number > 0) {
                map.put(product, number);
            }
        });

        List<Product> sortedList = new ArrayList<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .forEach(product -> sortedList.add(product.getKey()));
        return sortedList;
    }

    private List<Product> findValidProducts() {
        List<Product> result = this.productList.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
        result.forEach(product -> product = new Product(product));
        return result;
    }

    private List<Product> sortProducts(String sort, String order, List<Product> products) {
        Objects.requireNonNull(products, "Collection should not be null");
        List<Product> result = products;

        if ("description".equalsIgnoreCase(sort)) {
            result = result
                    .stream()
                    .sorted("asc".equalsIgnoreCase(order)
                            ? Comparator.comparing(Product::getDescription)
                            : Comparator.comparing(Product::getDescription).reversed())
                    .collect(Collectors.toList());
        } else if ("price".equalsIgnoreCase(sort)) {
            result = result
                    .stream()
                    .sorted("asc".equalsIgnoreCase(order)
                            ? Comparator.comparing(Product::getPrice)
                            : Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        }
        return result;
    }
}
