package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    private final String imageUrl = "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg";

    private final Currency usd = Currency.getInstance("USD");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResultsIfNoProducts() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsSuccess() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));

        assertNotNull(productDao.findProducts());
        assertEquals(productDao.findProducts().size(), 1);
    }

    @Test
    public void testSaveIsSuccess() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));

        assertNotNull(productDao.findProducts());
        assertEquals(productDao.findProducts().get(1).getCode(), "sgs");
    }


    @Test
    public void testDeleteSuccess() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl));

        productDao.delete(1L);

        assertNotNull(productDao.findProducts());
        assertEquals(productDao.findProducts().size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductException() {
        productDao.getProduct(1L);
    }

    @Test
    public void testDeleteExceptionIfIdNotFound(){
        Product product = new Product(1L, "sgs", "Samsung Galaxy S",
                new BigDecimal(100), usd, 100, imageUrl);

        productDao.save(product);

        thrown.expect(NoSuchElementException.class);
        productDao.delete(2L);
    }
}
