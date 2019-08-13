package com.es.phoneshop.model.product;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceHistory implements Serializable {
    private String date;
    private BigDecimal price;

    public PriceHistory(PriceHistory priceHistory) {
        this(priceHistory.getDate(), priceHistory.getPrice());
    }
}
