<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Advanced search">

    <c:if test="${not empty error}">
        <br><span style="color:red">${error}</span>
    </c:if>

    <br>
    <c:url value="/advancedSearch" var="advancedSearch"/>
    <form method="post" action="${advancedSearch}">
        <p>
            <label for="description">Description:</label>
            <input id="description" name="description" value="${param.description}">
            <c:if test="${not empty descriptionError}">
                <span style="color: red">${descriptionError}</span>
            </c:if>
        </p>
        <p>
            <label for="minPrice">Min price:</label>
            <input id="minPrice" name="minPrice" value="${param.minPrice}">
            <c:if test="${not empty minPriceError}">
                <span style="color: red">${minPriceError}</span>
            </c:if>
        </p>
        <p>
            <label for="maxPrice">Max price:</label>
            <input id="maxPrice" name="maxPrice" value="${param.maxPrice}">
            <c:if test="${not empty maxPriceError}">
                <span style="color: red">${maxPriceError}</span>
            </c:if>
        </p>
        <p>
            <label for="minStock">Min stock:</label>
            <input id="minStock" name="minStock" value="${param.minStock}">
            <c:if test="${not empty minStockError}">
                <span style="color: red">${minStockError}</span>
            </c:if>
        </p>
        <p>
            <label for="maxStock">Max stock:</label>
            <input id="maxStock" name="maxStock" value="${param.maxStock}">
            <c:if test="${not empty maxStockError}">
                <span style="color: red">${maxStockError}</span>
            </c:if>
        </p>
        <p>
            <button>Search</button>
        </p>
    </form>
    <c:if test="${empty error}">
        <br><span style="color:forestgreen">Successfull search</span>

        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
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
    </c:if>
</tags:master>