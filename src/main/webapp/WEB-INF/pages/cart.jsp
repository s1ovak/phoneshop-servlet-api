<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">

    <p>Cart</p>
    <c:if test="${not empty errors}">
        <span style="color:red">Error updating cart</span>
    </c:if>

    <c:if test="${not empty param.message}">
        <span style="color:forestgreen">${param.message}</span>
    </c:if>

    <c:url value="/cart" var="cartPage"/>
    <form method="post" action="${cartPage}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td>Quantity</td>
                <td class="price">Price</td>
            </tr>
            </thead>

            <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                <c:set var="product" value="${cartItem.product}"/>
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </td>
                    <td>
                        <c:url value="/products/${product.id}" var="productPage"/>
                        <a href="${productPage}">${product.description}</a>
                    </td>
                    <td>
                        <input name="quantity"
                               value="${not empty paramValues.quantity[status.index] ? paramValues.quantity[status.index] : cartItem.quantity}"
                               style="text-align: right">
                        <input type="hidden" name="productId" value="${product.id}">
                        <c:if test="${not empty errors[status.index]}">
                            <br><span style="color:red">${errors[status.index]}</span>
                        </c:if>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="3" style="text-align: right">Total</td>
                <td>$${cart.totalCost}</td>
            </tr>
        </table>

        <br>
        <button>Update</button>
    </form>

</tags:master>
