package com.es.phoneshop.model.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceHistory {
    private String date;
    private BigDecimal price;

    public PriceHistory(PriceHistory priceHistory) {
        this(priceHistory.getDate(), priceHistory.getPrice());
    }
}
