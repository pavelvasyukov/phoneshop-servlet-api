<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="productHistory" type="java.util.List" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p>
<table>
    <c:forEach var="viewedProduct" items="${productHistory}">
        <td>
            <img class="product-tile"
                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewedProduct.imageUrl}">
            <p>${viewedProduct.getDescription()}</p>
            <p class="price">
                <fmt:formatNumber value="${viewedProduct.getPrice()}" type="currency"
                                  currencySymbol="${viewedProduct.getCurrency().symbol}"/>
            </p>
        </td>
    </c:forEach>
</table>
</p>