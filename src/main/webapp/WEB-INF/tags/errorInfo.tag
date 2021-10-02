<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="error" required="true" %>
<%@ attribute name="messageSuccess" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty messageSuccess}">
    <c:set var="messageAboutAdd" value="${messageSuccess}"/>
    <c:set var="messageAboutAddStyle" value="success"/>
</c:if>

<c:if test="${not empty error}">
    <c:set var="messageAboutAdd" value="${error}"/>
    <c:set var="messageAboutAddStyle" value="error"/>
</c:if>

<div class="${messageAboutAddStyle}">
    ${messageAboutAdd}
</div>