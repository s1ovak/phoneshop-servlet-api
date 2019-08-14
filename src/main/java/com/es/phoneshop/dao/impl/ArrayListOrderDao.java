package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {
    private static ArrayListOrderDao instance;

    public static synchronized ArrayListOrderDao getInstance() {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    private ArrayListOrderDao() {
    }


    private List<Order> orders = new ArrayList<>();

    @Override
    public synchronized void save(Order order) {
        if (orders.stream()
                .anyMatch(o -> o.getSecureId().equals(order.getSecureId()))) {
            throw new IllegalArgumentException("Order with such ID already exists");
        } else {
            orders.add(order);
        }
    }

    @Override
    public synchronized Order getOrder(String secureId) {
        return orders.stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }
}
