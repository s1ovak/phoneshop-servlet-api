package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.RecentViews;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.RecentViewsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

public class RecentViewsServiceImpl implements RecentViewsService {
    private static RecentViewsServiceImpl instance;

    public static synchronized RecentViewsServiceImpl getInstance() {
        if (instance == null) {
            instance = new RecentViewsServiceImpl();
        }
        return instance;
    }

    private RecentViewsServiceImpl() {
    }

    @Override
    public RecentViews getRecentViews(HttpServletRequest request) {
        Objects.requireNonNull(request, "Request should not be null");

        HttpSession session = request.getSession();
        RecentViews recentViews = (RecentViews) session.getAttribute("recentViews");
        if (recentViews == null) {
            recentViews = new RecentViews();
            session.setAttribute("recentViews", recentViews);
        }
        return recentViews;
    }

    @Override
    public void add(RecentViews recentViews, long productId) {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        Deque<Product> recentViewsProductList = recentViews.getRecentyViewedProducts();

        Optional<Product> productOptional = recentViewsProductList.stream()
                .filter(p -> Long.valueOf(productId).equals(p.getId()))
                .findAny();

        recentViewsProductList.addFirst(product);
        if (productOptional.isPresent()) {
            recentViewsProductList.removeLastOccurrence(product);
        } else if (recentViewsProductList.size() > 3) {
            recentViewsProductList.removeLast();
        }
    }
}
