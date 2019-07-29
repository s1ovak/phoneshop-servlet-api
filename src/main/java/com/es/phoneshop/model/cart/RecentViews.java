package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.Deque;
import java.util.LinkedList;

import lombok.Getter;

@Getter
public class RecentViews {
    private Deque<Product> recentyViewedProducts = new LinkedList<>();
}
