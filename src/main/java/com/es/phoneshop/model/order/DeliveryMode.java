package com.es.phoneshop.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum DeliveryMode {
    COURIER("Courier", BigDecimal.valueOf(10)), PICKUP("Pickup", BigDecimal.ZERO);

    private String description;
    private BigDecimal deliveryPrice;

    public static List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    public static DeliveryMode getDeliveryMode(String description) {
        return getDeliveryModes().stream()
                .filter(deliveryMode -> description.equals(deliveryMode.getDescription()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
