package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String id = uri
                .substring(uri.indexOf(request.getServletPath()) + request.getServletPath().length() + 1);

        request.setAttribute("product", productDao.getProduct(Long.valueOf(id)));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }
}
