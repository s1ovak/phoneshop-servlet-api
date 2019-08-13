package com.es.phoneshop.model.product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Product implements Serializable {
    private Long id;
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private List<PriceHistory> priceHistories;

    public Product(Product otherProduct) {
        this(otherProduct.getId(), otherProduct.getCode(), otherProduct.getDescription(),
                otherProduct.getPrice(), otherProduct.getCurrency(), otherProduct.getStock(),
                otherProduct.getImageUrl(),
                otherProduct.getPriceHistories()
                        .stream()
                        .map(priceHistory -> priceHistory = new PriceHistory(priceHistory))
                        .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Product{"
                + "id='" + id + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
