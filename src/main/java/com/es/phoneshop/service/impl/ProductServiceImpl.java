package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.ProductService;

import java.util.Comparator;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static ProductServiceImpl instance;

    public static synchronized ProductServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProductServiceImpl();
        }
        return instance;
    }

    private ProductServiceImpl() {
    }

    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    public Product getProduct(Long id) {
        return productDao.getProduct(id);
    }

    @Override
    public List<Product> findProducts(String query, String sort, String order) {
        return productDao.searchValidProducts(query, createComparatorForSort(sort, order));
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }

    private Comparator<Product> createComparatorForSort(String sort, String order) {
        Comparator<Product> productComparator = null;
        if (sort != null && !sort.trim().isEmpty() && order != null && !order.trim().isEmpty()) {
            if ("description".equalsIgnoreCase(sort)) {
                productComparator = "asc".equalsIgnoreCase(order)
                        ? Comparator.comparing(Product::getDescription)
                        : Comparator.comparing(Product::getDescription).reversed();
            } else if ("price".equalsIgnoreCase(sort)) {
                productComparator = "asc".equalsIgnoreCase(order)
                        ? Comparator.comparing(Product::getDescription)
                        : Comparator.comparing(Product::getDescription).reversed();
            }
        }
        return productComparator;
    }
}
