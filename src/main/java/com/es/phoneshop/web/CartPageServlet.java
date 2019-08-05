package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.impl.HttpSessionCartService;
import com.es.phoneshop.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {
    private static final String CART = "cart";
    private static final String PRODUCT_ID = "productId";
    private static final String QUANTITY = "quantity";
    private static final String ERRORS = "errors";

    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        productService = ProductServiceImpl.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues(PRODUCT_ID);
        String[] quantities = request.getParameterValues(QUANTITY);

        Cart cart = cartService.getCart(request);

        String[] errors = new String[productIds.length];
        for (int i = 0; i < productIds.length; i++) {
            long productId = Long.valueOf(productIds[i]);
            Integer quantity = parseQuantity(quantities, errors, i);

            if (quantity != null) {
                try {
                    cartService.update(cart, productId, quantity);
                } catch (OutOfStockException exception) {
                    errors[i] = exception.getMessage();
                }
            }
        }

        boolean hasError = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasError) {
            request.setAttribute(ERRORS, errors);
            doGet(request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?message=Updated successfully");
        }
    }

    private Integer parseQuantity(String[] quantities, String[] errors, int i) {
        try {
            return Integer.valueOf(quantities[i]);
        } catch (NumberFormatException exception) {
            errors[i] = "Not a number";
        }
        return null;
    }
}
