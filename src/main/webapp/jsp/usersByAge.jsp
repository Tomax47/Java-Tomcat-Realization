<%@ page import="model.User" %>
<%@ page import="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>UsersByAge</title>
</head>
<body>

<form action="/getUsersByAge" method="post">
    <label for="age">Enter Age:</label>
    <input type="number" id="age" name="age">
    <input type="submit" value="Submit">
</form>

<h2>Users with Age <%= request.getParameter("age") %>:</h2>

<div>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Age</th>
            <th>Email</th>
        </tr>
        <c:forEach items="${usersForJspAge}" var="user">
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
