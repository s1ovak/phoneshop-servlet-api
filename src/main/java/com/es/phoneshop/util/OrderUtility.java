package com.es.phoneshop.util;

import com.es.phoneshop.model.order.ContactDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

public class OrderUtility {
    public static Optional<ContactDetails> getContactDetails(HttpServletRequest request) {
        boolean hasError = false;

        String firstName = request.getParameter("firstName");
        if (isInvalid(firstName)) {
            hasError = true;
            request.setAttribute("firstNameError", "First name is required");
        }

        String lastName = request.getParameter("lastName");
        if (isInvalid(lastName)) {
            hasError = true;
            request.setAttribute("lastNameError", "Last name is required");
        }

        String phone = request.getParameter("phone");
        if (isInvalid(phone) || !isValidPhone(phone)) {
            hasError = true;
            request.setAttribute("phoneError", "Phone number is required");
        }

        String address = request.getParameter("address");
        if (isInvalid(address)) {
            hasError = true;
            request.setAttribute("addressError", "Delivery address is required");
        }

        if (hasError) {
            return Optional.empty();
        } else {
            return Optional.of(new ContactDetails(firstName, lastName, phone, address));
        }
    }

    private static boolean isInvalid(String s) {
        return s == null || s.isEmpty();
    }

    private static boolean isValidPhone(String phone) {
        String regex = "\\+\\d{12}";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(phone).matches();
    }
}
