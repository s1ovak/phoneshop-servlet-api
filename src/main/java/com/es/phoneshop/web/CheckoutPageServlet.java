package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.ContactDetails;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.HttpSessionCartService;
import com.es.phoneshop.service.impl.OrderServiceImpl;
import com.es.phoneshop.util.OrderUtility;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CheckoutPageServlet extends HttpServlet {
    private static final String CART = "cart";
    private static final String ORDER = "order";
    private static final String DELIVERY = "deliveryModes";
    private static final String PAYMENT = "paymentMethods";

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute(ORDER, orderService.createOrder(cart));
        request.setAttribute(DELIVERY, DeliveryMode.getDeliveryModes());
        request.setAttribute(PAYMENT, PaymentMethod.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.createOrder(cart);
        ContactDetails contactDetails;

        Optional<ContactDetails> contactDetailsOptional = OrderUtility.getContactDetails(request);
        if (contactDetailsOptional.isPresent()) {
            contactDetails = contactDetailsOptional.get();
        } else {
            doGet(request, response);
            return;
        }

        String deliveryModeString = request.getParameter("deliveryMode");
        DeliveryMode deliveryMode = DeliveryMode.getDeliveryMode(deliveryModeString);

        String paymentMethodString = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.getPaymentMethod(paymentMethodString);

        orderService.placeOrder(order, contactDetails, deliveryMode, paymentMethod);
        cartService.clearCart(cart);

        response.sendRedirect(request.getRequestURI() + "/order/overview/" + order.getSecureId());
    }
}
