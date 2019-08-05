package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CartItem implements Serializable {
    private Product product;
    private int quantity;
}
