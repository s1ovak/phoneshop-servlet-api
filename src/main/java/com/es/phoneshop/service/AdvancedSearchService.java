package com.es.phoneshop.service;

import com.es.phoneshop.model.advancedSearch.SearchParams;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface AdvancedSearchService {
    List<Product> findProducts(SearchParams params);
}
