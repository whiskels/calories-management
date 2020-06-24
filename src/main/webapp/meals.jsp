<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Meals</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="header">
    <h3><a class="link" href="index.html">Return home</a></h3>
</div>
<h2 class="text">Meals</h2>
<table class="content-table">
    <thead>
    <tr>
        <th>#</th>
        <th>ID</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Excess</th>
        <th>Date</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>

    <c:set var="i" value="1"/>
    <%--@elvariable id="mealList" type="java.util.List"--%>
    <c:forEach var="meal" items="${mealList}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr>
            <td>${i}</td>
            <td>${meal.id}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td class="${meal.excess ? "red" : "green"}">${meal.excess ? 'yes' : 'no'}</td>
            <td><javatime:format pattern="dd-MM-yyyy HH:mm" value="${meal.dateTime}"/></td>
            <td><a href="meals?action=edit&id=${meal.id}">
                <button class="text">Edit</button>
            </a></td>
            <td><a href="meals?action=delete&id=${meal.id}">
                <button class="text">Delete</button>
            </a></td>
        </tr>
        <c:set var="i" value="${i + 1}"/>
    </c:forEach>
    </tbody>
</table>
<div class="header">
    <h2>Add new meal</h2>
    <form method="post" action="meals?action=create">
        <input class="text" type="text" placeholder="Description" name="description">
        <input class="text" type="number" placeholder="Calories" name="calories">
        <input class="text" type="datetime-local" placeholder="Date" name="dateTime">
        <input class="text" type="submit" value="Add">
    </form>
</div>
</body>
</html>