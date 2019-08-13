package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.ContactDetails;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(
            Order order, ContactDetails contactDetails, DeliveryMode deliveryMode, PaymentMethod paymentMethod);
}
