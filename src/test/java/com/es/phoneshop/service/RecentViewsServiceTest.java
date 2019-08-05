package com.es.phoneshop.service;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.RecentViews;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.impl.RecentViewsServiceImpl;
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
public class RecentViewsServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private RecentViewsService recentViewsService = RecentViewsServiceImpl.getInstance();
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private RecentViews recentViews = new RecentViews();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(recentViews);
        List<PriceHistory> priceHistories = new ArrayList<>();
        priceHistories.add(new PriceHistory("1 Set 2018", new BigDecimal(100)));
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, null, priceHistories));
        productDao.save(new Product(2L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, null, priceHistories));
        productDao.save(new Product(3L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, null, priceHistories));
        productDao.save(new Product(4L, "sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, null, priceHistories));
    }

    @After
    public void clear() {
        productDao.clearAll();
    }

    @Test
    public void testGetRecentViews() {
        recentViewsService.getRecentViews(request);
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    public void testAdd() {
        recentViewsService.add(recentViews, 1L);
        recentViewsService.add(recentViews, 2L);
        recentViewsService.add(recentViews, 1L);

        assertEquals(2, recentViewsService.getRecentViews(request).getRecentyViewedProducts().size());

        recentViewsService.add(recentViews, 3L);
        recentViewsService.add(recentViews, 4L);

        assertEquals(3, recentViewsService.getRecentViews(request).getRecentyViewedProducts().size());

        assertEquals((Long) 4L, recentViewsService.getRecentViews(request).getRecentyViewedProducts()
                .getFirst().getId());
    }
}
