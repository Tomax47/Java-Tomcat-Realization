<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Email</title>
</head>
<body>

<form action="/getUserByEmail" method="post">
    <label>Enter Email:</label>
    <input type="text" id="email" name="email">
    <input type="submit" value="Submit">
</form>

<c:if test="${not empty usersForJspEmail}">
    <h2>User with Email <%= request.getParameter("email") %>:</h2>

    <div>
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Age</th>
                <th>Email</th>
            </tr>
            <c:forEach var="user" items="${usersForJspEmail}">
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
</c:if>

</body>
</html>
