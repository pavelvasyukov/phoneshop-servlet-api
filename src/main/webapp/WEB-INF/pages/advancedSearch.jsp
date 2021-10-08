<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="searchProducts" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <p>
            Description
            <input name="query" value="${param.query}">

            <select name="searchParameters">
                <option value="allWord">all word</option>
                <option value="anyWord">any word</option>
            </select>
        </p>
        <p>
            Min price <input name="minPrice" value="${param.minPrice}">
            <tags:errorInfo error="${error}" messageSuccess="${message}"/>
        </p>
        <p>
            Max price <input name="maxPrice" value="${param.maxPrice}">
            <tags:errorInfo error="${error}" messageSuccess="${message}"/>
        </p>
        <button>Search</button>
    </form>

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
            </td>
            <td class="price">Price

        </tr>
        </thead>
        <c:forEach var="product" items="${searchProducts}" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                        <a/>
                </td>

                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>

            </tr>
        </c:forEach>

    </table>

</tags:master>