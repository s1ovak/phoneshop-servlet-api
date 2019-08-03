package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import lombok.Getter;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

@Getter
public class RecentViews {
    private Deque<Product> recentyViewedProducts = new ConcurrentLinkedDeque<>();
}
