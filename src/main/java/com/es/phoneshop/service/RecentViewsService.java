package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.RecentViews;

import javax.servlet.http.HttpServletRequest;

public interface RecentViewsService {
    RecentViews getRecentViews(HttpServletRequest request);

    void add(RecentViews recentViews, long productId);
}
