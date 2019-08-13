package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.ContactDetails;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.service.OrderService;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance;

    public static synchronized OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    private OrderServiceImpl() {
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems().stream()
                .map(CartItem::new).collect(Collectors.toList()));
        order.setProductsCost(cart.getTotalCost());

        return order;
    }

    @Override
    public void placeOrder(Order order, ContactDetails contactDetails, DeliveryMode deliveryMode, PaymentMethod paymentMethod) {
        order.setSecureId(generateUniqueId());
        order.setContactDetails(contactDetails);
        order.setDeliveryMode(deliveryMode);
        order.setDeliveryCost(deliveryMode.getDeliveryPrice());
        order.setPaymentMethod(paymentMethod);
        calculateTotalOrderCost(order);

        ArrayListOrderDao.getInstance().save(order);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private void calculateTotalOrderCost(Order order) {
        BigDecimal totalOrderCost = order.getTotalCost().add(order.getDeliveryCost());
        order.setTotalOrderCost(totalOrderCost);
    }
}
