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

public class ProductDetailsPageServlet extends HttpServlet {
    static final String QUANTITY = "quantity";
    static final String ERROR = "error";
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        productService = ProductServiceImpl.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("product", productService.getProduct(getIdFromPath(request)));
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getIdFromPath(request);
        int quantity;

        try {
            quantity = Integer.valueOf(request.getParameter(QUANTITY));
        } catch (NumberFormatException exception) {
            request.setAttribute(ERROR, "Not a number");
            doGet(request, response);
            return;
        }

        Cart cart = cartService.getCart(request);

        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException exception) {
            request.setAttribute(ERROR, exception.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI()
                + "?message=Added succesfully&quantity=" + quantity);
    }

    private Long getIdFromPath(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
