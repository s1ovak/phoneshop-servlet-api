<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Order">

    <p>
    <h3>Order</h3></p>
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
    <p>
    <h3>Order information</h3></p>

    <p>
        First name: ${order.contactDetails.firstName}
    </p>
    <p>
        Last name: ${order.contactDetails.lastName}
    </p>
    <p>
        Phone: ${order.contactDetails.phone}
    </p>
    <p>
        Delivery address: ${order.contactDetails.deliveryAddress}
    </p>
    <p>
        Products cost: $${order.productsCost}
    </p>
    <p>
        Delivery: ${order.deliveryMode.description}
    </p>
    <p>
        Delivery cost: $${order.deliveryCost}
    </p>
    <p>
        Payment method: ${order.paymentMethod.description}
    </p>
    <p><h4>Total order cost: $${order.totalOrderCost}</h4></p>

</tags:master>