package com.es.phoneshop.model.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
public class Cart implements Serializable {
    private List<CartItem> cartItems = Collections.synchronizedList(new ArrayList<>());
    private BigDecimal totalCost = new BigDecimal(0);
    private int totalQuantity = 0;

    public void clearAll() {
        this.cartItems.clear();
    }
}
