<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Order">

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Quantity</td>
            <td class="price">Price</td>
        </tr>
        </thead>

        <c:forEach var="cartItem" items="${order.cartItems}">
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
                        ${cartItem.quantity}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="3" style="text-align: right">Total</td>
            <td>$${order.productsCost}</td>
        </tr>
    </table>

    <br>
    <c:url value="/checkout" var="checoutUrl"/>
    <form method="post" action="${checoutUrl}">
        <p>
            <label for="firstName">First Name:</label>
            <input id="firstName" name="firstName" value="${param.firstName}">
            <c:if test="${not empty firstNameError}">
                <span style="color: red">${firstNameError}</span>
            </c:if>
        </p>
        <p>
            <label for="lastName">Last Name:</label>
            <input id="lastName" name="lastName" value="${param.lastName}">
            <c:if test="${not empty lastNameError}">
                <span style="color: red">${lastNameError}</span>
            </c:if>
        </p>
        <p>
            <label for="phone">Phone:</label>
            <input id="phone" name="phone" value="${param.phone}">
            <c:if test="${not empty phoneError}">
                <span style="color: red">${phoneError}</span>
            </c:if>
        </p>
        <p>
            <label for="address">Delivery address:</label>
            <input id="address" name="address" value="${param.address}">
            <c:if test="${not empty addressError}">
                <span style="color: red">${addressError}</span>
            </c:if>
        </p>
        <p>
            <label for="deliveryMode">Delivery:</label>
            <select id="deliveryMode" name="deliveryMode">
                <c:forEach var="deliveryMode" items="${deliveryModes}">
                    <option name="${deliveryMode}">${deliveryMode.description}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label for="paymentMethod">Payment method:</label>
            <select id="paymentMethod" name="paymentMethod">
                <c:forEach var="paymentMethod" items="${paymentMethods}">
                    <option name="${paymentMethod}">${paymentMethod.description}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <button>Place order</button>
        </p>
    </form>

</tags:master>
