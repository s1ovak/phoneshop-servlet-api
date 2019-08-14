package com.es.phoneshop.model.order;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetails {
    private String firstName;
    private String lastName;
    private String phone;
    private String deliveryAddress;
}
