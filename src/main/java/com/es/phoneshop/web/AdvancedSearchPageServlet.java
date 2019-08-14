package com.es.phoneshop.web;

import com.es.phoneshop.model.advancedSearch.SearchParams;
import com.es.phoneshop.service.AdvancedSearchService;
import com.es.phoneshop.service.impl.AdvancedSearchServiceImpl;
import com.es.phoneshop.util.AdvancedSearchUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AdvancedSearchPageServlet extends HttpServlet {
    private AdvancedSearchService advancedSearchService;

    @Override
    public void init() throws ServletException {
        advancedSearchService = AdvancedSearchServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SearchParams searchParams;

        Optional<SearchParams> searchParamsOptional = AdvancedSearchUtility
                .getSearchParams(request);
        if (searchParamsOptional.isPresent()) {
            searchParams = searchParamsOptional.get();
        } else {
            doGet(request, response);
            return;
        }

        request.setAttribute("products", advancedSearchService.findProducts(searchParams));
        doGet(request, response);
    }
}
