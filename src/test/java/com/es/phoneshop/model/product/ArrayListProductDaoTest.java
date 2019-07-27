package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.impl.ProductServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private List<PriceHistory> priceHistories;
    private ProductService productService;
    private final String imageUrl = "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg";

    private final Currency usd = Currency.getInstance("USD");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        productService = ProductServiceImpl.getInstance();
        priceHistories = new ArrayList<>();
        priceHistories.add(new PriceHistory("1 Set 2018", new BigDecimal(100)));
        priceHistories.add(new PriceHistory("10 Oct 2018", new BigDecimal(110)));
        priceHistories.add(new PriceHistory("10 Jan 2019", new BigDecimal(150)));
    }

    @After
    public void clear() {
        productDao.clearAll();
    }

    @Test
    public void testFindProductsNoResultsIfParamsNullAndNoProducts() {
        assertTrue(productDao.searchValidProducts(null, null).isEmpty());
        assertTrue(productService.findProducts("1", null, "2").isEmpty());
        assertTrue(productService.findProducts("1", "2", null).isEmpty());
    }

    @Test
    public void testFindProductsSuccess() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl, priceHistories));

        assertNotNull(productService.findProducts("s", "price", "asc"));
        assertEquals(productService.findProducts("s", "price", "asc").size(), 1);
    }

    @Test
    public void testSaveIsSuccess() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl, priceHistories));

        assertNotNull(productService.findProducts("s", "price", "asc"));
        assertEquals(productService.findProducts("s", null, null).get(0).getCode(), "sgs");
    }


    @Test
    public void testDeleteSuccess() {
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl, priceHistories));

        productDao.delete(1L);

        assertNotNull(productService.findProducts("s", "price", "asc"));
        assertEquals(productService.findProducts("s", "price", "asc").size(), 0);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductException() {
        productDao.getProduct(1L);
    }

    @Test
    public void testDeleteExceptionIfIdNotFound() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S",
                new BigDecimal(100), usd, 100, imageUrl, priceHistories);

        productDao.save(product);

        thrown.expect(ProductNotFoundException.class);
        productDao.delete(2L);
    }

    @Test
    public void testSearchProductByQuery() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S",
                new BigDecimal(100), usd, 100, imageUrl, priceHistories);
        Product product1 = new Product(2L, "sgs", "qwe",
                new BigDecimal(100), usd, 100, imageUrl, priceHistories);

        productDao.save(product);
        productDao.save(product1);

        assertNotNull(productService.findProducts("s", null, null));
        assertEquals(productService.findProducts("s", null, null).size(), 1);
        assertEquals(productService.findProducts("s", null, null).get(0).getCode(), "sgs");
    }

    @Test
    public void testSortingByDescriptionAndPriceSuccess() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S",
                new BigDecimal(100), usd, 100, imageUrl, priceHistories);
        Product product1 = new Product(2L, "sgs", "qwe",
                new BigDecimal(130), usd, 100, imageUrl, priceHistories);
        Product product2 = new Product(3L, "sgs", "asd",
                new BigDecimal(80), usd, 100, imageUrl, priceHistories);

        productDao.save(product);
        productDao.save(product1);
        productDao.save(product2);

        assertNotNull(productService.findProducts("s", "price", "asc"));
        assertEquals(productService.findProducts("s", "price", "asc").get(1).getPrice(), new BigDecimal(80));
        assertEquals(productService.findProducts(null, "description", "desc").get(0).getDescription(), "qwe");
    }
}
