<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <a href="?query=${param.query}&sort=description&order=asc">&dArr;</a>
                <a href="?query=${param.query}&sort=description&order=desc">&uArr;</a>
            </td>
            <td class="price">Price
                <a href="?query=${param.query}&sort=price&order=asc">&dArr;</a>
                <a href="?query=${param.query}&sort=price&order=desc">&uArr;</a>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <a href="products/${product.id}">
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </a>
                </td>
                <td>
                    <a href="products/${product.id}">${product.description}</a></td>
                <td class="price">
                    <a href="<c:url value="/products/history/${product.id}"/>">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>

