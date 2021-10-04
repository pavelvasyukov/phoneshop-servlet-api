<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">

    <c:if test="${not empty errors}">
        Error occurred while placing order
    </c:if>
    <p>

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
        <c:forEach var="item" items="${order.items}" varStatus="status">
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
                <td class="quantity">
                    <fmt:formatNumber value="${item.quantity}" var="quantity"/>
                        ${item.quantity}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${item.product.price}" type="currency"
                                      currencySymbol="${item.product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td>Subtotal:</td>
            <td class="price">
                <fmt:formatNumber value="${order.subtotal}" type="currency"
                                  currencySymbol="${order.currency}"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>Delivery Cost:</td>
            <td class="price">
                <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                  currencySymbol="${order.currency}"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>Total cost:</td>
            <td class="price">
                <fmt:formatNumber value="${order.totalCost}" type="currency"
                                  currencySymbol="${order.currency}"/>
            </td>
        </tr>
    </table>
    <h2>Your details</h2>

    <table>
        <tr>
            <td>First Name</td>
            <td>
                    ${order.firstName}
            </td>
        </tr>

        <tr>
            <td>Last Name</td>
            <td>
                    ${order.lastName}
            </td>
        </tr>
        <tr>
            <td>Phone</td>
            <td>
                    ${order.phone}
            </td>
        </tr>

        <tr>
            <td>Delivery Date}</td>
            <td>
                    ${order.deliveryDate}
            </td>
        </tr>

        <tr>
            <td>Delivery Address</td>
            <td>
                    ${order.deliveryAddress}
            </td>
        </tr>
        <tr>
            <td>Payment method</td>
            <td>
                    ${order.paymentMethod}
            </td>
        </tr>
    </table>
    <p>
        <button>Place order</button>
    </p>


</tags:master>