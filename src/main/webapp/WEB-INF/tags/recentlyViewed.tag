<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recentProducts" required="true" type="java.util.concurrent.ConcurrentLinkedDeque" %>

<c:if test="${not empty recentProducts}">
    <br>
    <h3>Recently Viewed</h3>
    <table>
        <thead>
        <c:forEach var="product1" items="${recentProducts}">
            <th>
            <td align="center">
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product1.imageUrl}">
                <br>
                <c:url value="/products/${product1.id}" var="productPage"/>
                <a href="${productPage}">${product1.description}</a>
                <br>
                <fmt:formatNumber value="${product1.price}" type="currency"
                                  currencySymbol="${product1.currency.symbol}"/>
            </td>
            </th>
        </c:forEach>
        </thead>
    </table>
</c:if>
