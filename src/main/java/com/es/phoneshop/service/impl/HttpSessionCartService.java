package com.es.phoneshop.service.impl;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static HttpSessionCartService instance;

    //private Cart cart;

    private HttpSessionCartService() {
        //cart = new Cart();
    }

    public static synchronized HttpSessionCartService getInstance() {
        if (instance == null) {
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Objects.requireNonNull(request, "Request should not be null");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, long productId, int quantity) {
        Product product = ProductServiceImpl.getInstance().getProduct(productId);

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> Long.valueOf(productId).equals(cartItem.getProduct().getId()))
                .findAny();

        int totalQuantity = cartItemOptional.map(c -> c.getQuantity() + quantity).orElse(quantity);

        if (totalQuantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException(
                    "Not enough stock or incorrect stock input. Product stock is " + product.getStock());
        }

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            if ((cartItem.getQuantity() + quantity) > product.getStock()) {
                throw new OutOfStockException(
                        "Not enough stock. Product stock is " + product.getStock());
            }

            cartItem.setQuantity(totalQuantity);
        } else {
            CartItem cartItem = new CartItem(product, totalQuantity);
            cart.getCartItems().add(cartItem);
        }
        recalculateCart(cart, product, quantity);
    }

    private void recalculateCart(Cart cart, Product product, int quantity) {
        cart.setTotalCost(
                cart.getTotalCost().add(product.getPrice().multiply(new BigDecimal(quantity))));
        cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
    }
}
