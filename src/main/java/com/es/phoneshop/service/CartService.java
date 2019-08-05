package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    void add(Cart cart, long productId, int quantity);

    void update(Cart cart, long productId, int quantity);

    void delete(Cart cart, Long productId);
}
