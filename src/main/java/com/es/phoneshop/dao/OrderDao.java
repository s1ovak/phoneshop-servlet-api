package com.es.phoneshop.dao;

import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    void save(Order order);

    Order getOrder(String id);
}
