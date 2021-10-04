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
                    <fmt:formatNumber value="${item.product.price*item.quantity}" type="currency"
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
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
        <table>
            <tags:orderFormRow name="firstName" label="First Name" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="lastName" label="Last Name" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="phone" label="Phone" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="deliveryDate" label="Delivery Date" order="${order}" errors="${errors}"/>
            <tags:orderFormRow name="deliveryAddress" label="Delivery Address" order="${order}" errors="${errors}"/>
            <tr>
                <td>Payment method<span style="color: red">*</span></td>
                <td>
                    <select name="paymentMethod">
                        <option></option>
                        <c:forEach var="paymenyMethod" items="${paymentMethods}">
                            <c:choose>
                                <c:when test="${paymenyMethod eq order.paymentMethod}">
                                    <option selected>${paymenyMethod}</option>
                                </c:when>
                                <c:otherwise><option>${paymenyMethod}</option></c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <c:set var="error" value="${errors['paymentMethod']}"/>
                    <c:if test="${not empty error}">
                        <div class="error">${error}</div>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Place order</button>
        </p>
    </form>

</tags:master>