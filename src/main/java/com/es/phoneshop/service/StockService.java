package com.es.phoneshop.service;

import com.es.phoneshop.model.order.Order;

public interface StockService {
    void recalculateStock(Order order);
}
