<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Java Enterprise (Topjava)</title>
</head>
<body>
<h3>Проект <a href="https://github.com/JavaWebinar/topjava" target="_blank">Java Enterprise (Topjava)</a></h3>
<hr>
<form method="post" action="users">
    <b>Switch active user</b>
    <select name="userId">
        <option value="1">User#1</option>
        <option value="2">User#2</option>
    </select>
    <button type="submit">Apply</button>
</form>
<hr>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>E-mail</th>
        <th>Role</th>
    </tr>
    </thead>
    <c:forEach items="${users}" var="user">
        <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User"/>
        <tr>
            <td>${user.email}</td>
            <td>${user.roles}</td>
        </tr>
    </c:forEach>
</table>
</section>
</body>

</body>
</html>
