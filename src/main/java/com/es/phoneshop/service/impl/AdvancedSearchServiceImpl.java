package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.advancedSearch.SearchParams;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.AdvancedSearchService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvancedSearchServiceImpl implements AdvancedSearchService {
    private static AdvancedSearchServiceImpl instance;

    public static synchronized AdvancedSearchServiceImpl getInstance() {
        if (instance == null) {
            instance = new AdvancedSearchServiceImpl();
        }
        return instance;
    }

    private AdvancedSearchServiceImpl() {
    }

    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    public synchronized List<Product> findProducts(SearchParams params) {
        List<Product> result = productDao.findProducts();
        result = productDao.findProducts(params.getDescription(), result);
        result = filterProductsByPriceAndStock(
                params.getMinPrice(), params.getMaxPrice(), params.getMinStock(), params.getMaxStock(), result);
        return result;
    }

    private List<Product> filterProductsByPriceAndStock(
            Integer minPrice, Integer maxPrice, Integer minStock, Integer maxStock, List<Product> products) {
        Stream<Product> result = products.stream();
        if (minPrice != null) {
            result = result.filter(product -> product.getPrice().intValue() >= minPrice);
        }
        if (maxPrice != null) {
            result = result.filter(product -> product.getPrice().intValue() <= maxPrice);
        }
        if (minStock != null) {
            result = result.filter(product -> product.getPrice().intValue() >= minStock);
        }
        if (maxStock != null) {
            result = result.filter(product -> product.getPrice().intValue() <= maxStock);
        }

        return result.collect(Collectors.toList());
    }
}
