<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <a href="http://localhost:8080/phoneshop-servlet-api/products">Back to product list</a>
    <p>
        Product details
    </p>
    <c:if test="${not empty param.message}">
        <br><span style="color:forestgreen">${param.message}</span>
    </c:if>
    <div>
        <img src="${product.imageUrl}">
        <p>
            ${product.description}<br>
            Price: ${product.price}$<br>
            Current stock: ${product.stock}
        </p>
    </div>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <p>
            <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" style="text-align: right">
            <button>Add to cart</button>
            <c:if test="${not empty error}">
                <br><span style="color:red">${error}</span>
            </c:if>
        </p>
    </form>
    <tags:recentlyViewed recentProducts="${recentProducts}"/>
</tags:master>
