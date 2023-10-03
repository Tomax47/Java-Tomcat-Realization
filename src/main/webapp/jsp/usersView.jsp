<%@ page import="model.User" %>
<%@ page import="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>All Users</h1>

<div>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Age</th>
            <th>Email</th>
        </tr>
        <c:forEach items="${usersForJsp}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
