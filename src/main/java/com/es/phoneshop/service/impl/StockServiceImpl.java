package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.StockService;

public class StockServiceImpl implements StockService {
    private static StockServiceImpl instance;

    public static synchronized StockServiceImpl getInstance() {
        if (instance == null) {
            instance = new StockServiceImpl();
        }
        return instance;
    }

    private StockServiceImpl() {
    }


    @Override
    public synchronized void recalculateStock(Order order) {
        ProductDao productDao = ArrayListProductDao.getInstance();

        for (CartItem cartItem : order.getCartItems()) {
            Product product = productDao.getProduct(cartItem.getProduct().getId());
            int newStock = product.getStock() - cartItem.getQuantity();
            if (newStock >= 0) {
                product.setStock(newStock);
                productDao.save(product);
            } else {
                throw new OutOfStockException(
                        "Product " + product.getDescription() + " has not enough stock");
            }
        }

    }
}
