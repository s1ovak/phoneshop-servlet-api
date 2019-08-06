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

    private HttpSessionCartService() {
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
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            return cart;
        }
    }

    @Override
    public void add(Cart cart, long productId, int quantity) {
        Product product = ProductServiceImpl.getInstance().getProduct(productId);
        synchronized (cart) {
            Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                    .filter(cartItem -> product.equals(cartItem.getProduct()))
                    .findAny();

            int totalQuantity = cartItemOptional
                    .map(c -> c.getQuantity() + quantity).orElse(quantity);

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

            recalculateCart(cart);
        }
    }

    @Override
    public void update(Cart cart, long productId, int quantity) {
        Product product = ProductServiceImpl.getInstance().getProduct(productId);

        if (quantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException(
                    "Not enough stock. Product stock is " + product.getStock());
        }
        synchronized (cart) {
            CartItem cartItem = getCartItemFromCart(cart, productId);

            cartItem.setQuantity(quantity);

            recalculateCart(cart);
        }
    }

    @Override
    public void delete(Cart cart, Long productId) {
        synchronized (cart) {
            cart.getCartItems()
                    .removeIf(cartItem -> productId.equals(cartItem.getProduct().getId()));
            recalculateCart(cart);
        }
    }

    private void recalculateCart(Cart cart) {
        BigDecimal newTotalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));
        cart.setTotalCost(newTotalPrice);

        int newQuantity = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            newQuantity += cartItem.getQuantity();
        }
        cart.setTotalQuantity(newQuantity);
    }

    private CartItem getCartItemFromCart(Cart cart, long id) {
        return cart.getCartItems().stream()
                .filter(cartItem1 -> Long.valueOf(id).equals(cartItem1.getProduct().getId()))
                .findAny().get();
    }
}
