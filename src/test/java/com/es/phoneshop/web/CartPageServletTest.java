package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.HttpSessionCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession session;

    private CartPageServlet servlet = new CartPageServlet();
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private CartService cartService = HttpSessionCartService.getInstance();
    private Cart cart = new Cart();
    private String[] productIds = {"1"};
    private String[] quantities = {"5"};

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, OutOfStockException {
        Currency usd = Currency.getInstance("USD");
        List<PriceHistory> priceHistories = new ArrayList<>();
        priceHistories.add(new PriceHistory("1 Set 2018", new BigDecimal(100)));
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, "", priceHistories));

        when(request.getParameterValues("productId")).thenReturn(productIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        cartService.add(cart, 1L, 1);

        servlet.init(servletConfig);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
}
