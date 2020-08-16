<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><spring:message code="meal.title"/></h3>
    <hr/>
    <form method="get" action="${pageContext.request.contextPath}/meals/filter">
        <dl>
            <dt><spring:message code="meal.dateFrom"/></dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.dateTo"/></dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.timeFrom"/></dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.timeTo"/></dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">
            <dt><spring:message code="common.filter"/></dt>
        </button>
    </form>
    <hr/>
    <a href="${pageContext.request.contextPath}/meals/create"><spring:message code="meal.add"/></a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.date"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td>${fn:formatDateTime(meal.dateTime)}</td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="${pageContext.request.contextPath}/meals/update?id=${meal.id}"><spring:message
                        code="common.update"/></a></td>
                <td><a href="${pageContext.request.contextPath}/meals/delete?id=${meal.id}"><spring:message
                        code="common.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>