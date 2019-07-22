package com.es.phoneshop.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private Long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private ArrayList<PriceHistory> priceHistories;

    public Product(Product otherProduct) {
        this(otherProduct.getId(),otherProduct.getCode(),otherProduct.getDescription(),
                otherProduct.getPrice(), otherProduct.getCurrency(), otherProduct.getStock(),
                otherProduct.getImageUrl(), otherProduct.getPriceHistories());
    }
}
