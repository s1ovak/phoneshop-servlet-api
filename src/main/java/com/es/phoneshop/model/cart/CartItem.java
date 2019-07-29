package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {
    private Product product;
    private int quantity;
}
