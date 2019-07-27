package com.es.phoneshop.web;

import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("product", productService.getProduct(
                Long.valueOf(request.getPathInfo().substring(1))));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")
                .forward(request, response);
    }
}
