<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <p>
    <form method="post">
        <p class="success">${param.message}</p>
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description
                </td>
                <td>Quantity</td>
                <td class="price">Price
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.items}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                                ${item.product.description}
                            <a/>
                    </td>
                    <td>
                        <fmt:formatNumber value="${item.quantity}" var="quantity"/>
                        <input name="quantity"
                               value="${not empty errors[item.product.id] ? paramValues['quantity'][status.index]: item.quantity }"
                               class="quantity">
                        <input type="hidden" name="productId" value="${item.product.id}">

                        <c:if test="${not empty errors[item.product.id]}">
                            <tags:errorInfo error="${errors[item.product.id]}"
                                            messageSuccess="${param.message}"/>
                        </c:if>

                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                    <td>
                        <button form="deleteCartItem"
                                formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <p>
            <button>Update</button>
        </p>
    </form>
    <form method="post" id="deleteCartItem"></form>
    </p>
</tags:master>