package com.es.phoneshop.service;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.impl.HttpSessionCartService;
import com.es.phoneshop.service.impl.ProductServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private CartService cartService = HttpSessionCartService.getInstance();
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private Cart cart = new Cart();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);
        List<PriceHistory> priceHistories = new ArrayList<>();
        priceHistories.add(new PriceHistory("1 Set 2018", new BigDecimal(100)));
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, null, priceHistories));
    }

    @After
    public void clear() {
        productDao.clearAll();
        cart.getCartItems().clear();
    }

    @Test
    public void testGetCart() {
        cartService.getCart(request);
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    public void testAddSuccessful() throws OutOfStockException {
        assertEquals(0, cartService.getCart(request).getCartItems().size());


        cartService.add(cart, 1L, 1);
        assertEquals(1, cartService.getCart(request).getCartItems().size());

        cartService.add(cart, 1L, 1);
        assertEquals(2, cartService.getCart(request).getCartItems().get(0).getQuantity());
    }

    @Test(expected = OutOfStockException.class)
    public void testAddWithOverflow() throws OutOfStockException {
        cartService.add(cart, 1L, 101);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddIfIncorrectQuantity() throws OutOfStockException {
        cartService.add(cart, 1L, -5);
    }
}
