package com.es.phoneshop.model.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();
}
