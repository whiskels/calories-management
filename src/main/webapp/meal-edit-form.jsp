<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Edit meal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="header">
    <h3><a class="link" href="meals">Return to meals</a></h3>
</div>
<h2 class="text">Edit meal</h2>
<table class="content-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Date</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <form class="text" method="POST" action="meals?action=update">
        <tr>
            <%--@elvariable id="meal" type="ru.javawebinar.topjava.model.Meal"--%>
            <td>${meal.id}</td>
            <td><input class="text" type="text" name="description" value="${meal.description}"/></td>
            <td><input class="text" type="number" name="calories" value="${meal.calories}"/></td>
            <td><input class="text" type="datetime-local" name="dateTime" value="${meal.dateTime}"/></td>
            <td><input type="hidden" name="id" value="${meal.id}">
                <input class="text" type="submit" value="Save"></td>
        </tr>
    </form>
    </tbody>
</table>
</body>
</html>