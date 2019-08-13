package com.es.phoneshop.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    MONEY("Money"), CREDIT_CARD("Credit card");

    private String description;

    public static List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    public static PaymentMethod getPaymentMethod(String description) {
        return getPaymentMethods().stream()
                .filter(paymentMethod -> description.equals(paymentMethod.getDescription()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
