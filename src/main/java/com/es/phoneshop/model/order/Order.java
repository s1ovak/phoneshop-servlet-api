package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Order extends Cart {
    private String secureId;
    private ContactDetails contactDetails;
    private DeliveryMode deliveryMode;
    private PaymentMethod paymentMethod;
    private BigDecimal productsCost;
    private BigDecimal deliveryCost;
    private BigDecimal totalOrderCost;
}
