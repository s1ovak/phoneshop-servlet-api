<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <p>
        Product details
    </p>
    <div>
        <img src="${product.imageUrl}">
        <p>
            ${product.description}<br>
            Price: ${product.price}$<br>
            Current stock: ${product.stock}
        </p>
    </div>
</tags:master>
