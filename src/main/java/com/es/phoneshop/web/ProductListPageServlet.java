package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.RecentViewsService;
import com.es.phoneshop.service.impl.ProductServiceImpl;
import com.es.phoneshop.service.impl.RecentViewsServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ProductListPageServlet extends HttpServlet {
    static final String QUERY = "query";
    static final String SORT = "sort";
    static final String ORDER = "order";

    private ProductService productService;
    private RecentViewsService recentViewsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productService = ProductServiceImpl.getInstance();
        recentViewsService = RecentViewsServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY);
        String sort = request.getParameter(SORT);
        String order = request.getParameter(ORDER);

        request.setAttribute(
                "recentProducts", recentViewsService.getRecentViews(request).getRecentyViewedProducts());
        request.setAttribute("products", productService.findProducts(query, sort, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
