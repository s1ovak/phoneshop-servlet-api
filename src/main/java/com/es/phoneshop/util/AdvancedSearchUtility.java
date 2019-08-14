package com.es.phoneshop.util;

import com.es.phoneshop.model.advancedSearch.SearchParams;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AdvancedSearchUtility {
    public static Optional<SearchParams> getSearchParams(HttpServletRequest request) {
        Integer minPrice = checkParameter(request, "minPrice");
        Integer maxPrice = checkParameter(request, "maxPrice");
        Integer minStock = checkParameter(request, "minStock");
        Integer maxStock = checkParameter(request, "maxStock");

        if (minPrice == -1 || maxPrice == -1 || minStock == -1 || maxStock == -1) {
            request.setAttribute("error", "Incorrect params");
            return Optional.empty();
        } else {
            return Optional.of(new SearchParams(
                    request.getParameter("description"),
                    checkEmpty(minPrice), checkEmpty(maxPrice), checkEmpty(minStock), checkEmpty(maxStock)));
        }
    }

    private static Integer checkParameter(
            HttpServletRequest request, String paramName) {
        int paramValue = 0;

        try {
            String paramValueString = request.getParameter(paramName);
            if (paramValueString == null || paramValueString.trim().isEmpty()) {
                return 0;
            }
            paramValue = Integer.parseInt(paramValueString);
            if (paramValue < 0) {
                request.setAttribute(paramName + "Error", "Incorrect param " + paramName);
                paramValue = -1;
            }

        } catch (NumberFormatException ex) {
            request.setAttribute(paramName + "Error", "Incorrect param " + paramName);
            paramValue = -1;
        }
        return paramValue;
    }

    private static Integer checkEmpty(int i) {
        return i == 0 ? null : i;
    }
}
