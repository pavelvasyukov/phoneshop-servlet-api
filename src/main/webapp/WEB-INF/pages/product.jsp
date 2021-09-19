<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">

    <p>
        cart: ${cart}
    </p>
    <c:if test="${not empty param.message}">
        <c:set var="messageAboutAdd" value="${product.description} ${param.message}"/>
        <c:set var="messageAboutAddStyle" value="success"/>
    </c:if>

    <c:if test="${not empty error}">
        <c:set var="messageAboutAdd" value="Added to cart failure"/>
        <c:set var="messageAboutAddStyle" value="error"/>
    </c:if>

    <div class="${messageAboutAddStyle}">
            ${messageAboutAdd}
    </div>

    <p>${product.description}</p>
    <table>
        <tr>
            <td>Image</td>
            <td>
                <img src="${product.imageUrl}">
            </td>
        </tr>
        <tr>
            <td>Code</td>
            <td>
                    ${product.code}
            </td>
        </tr>
        <tr>
            <td>Stock</td>
            <td>
                    ${product.stock}
            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>

    <p>
    <form method="post">
        <span>quantity:</span>
        <input class="quantity" name="quantity" value="${not empty error ? param.quantity : 1}">
        <button>add to cart</button>
        <c:if test="${not empty error}">
            <div class="error">
                    ${error}
            </div>
        </c:if>

    </form>
    </p>

    <c:if test="${productHistory!=null}">
        <tags:productHistory productHistory="${productHistory}"></tags:productHistory>
    </c:if>
</tags:master>