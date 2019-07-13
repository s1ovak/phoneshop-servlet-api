package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Mock
    String imageUrl;

    Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProducts() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));
        assertEquals(productDao.findProducts().size(), 1);
    }

    @Test
    public void testSave() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));
        assertEquals(productDao.findProducts().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveException() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));
    }

    @Test
    public void testDelete() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));
        productDao.delete(1L);
        assertEquals(productDao.findProducts().size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductException() {
        productDao.getProduct(1L);
    }
}
