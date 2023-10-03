<%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 10/2/2023
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>

<form action="/users" method="post">
  <input type="hidden" name="action" value="register">

  <label>Name:</label>
  <input type="text" id="name" name="name" required><br><br>

  <label>Surname:</label>
  <input type="text" id="surname" name="surname" required><br><br>

  <label>Age:</label>
  <input type="number" id="age" name="age" required><br><br>

  <label for="email">Email:</label>
  <input type="text" id="email" name="email" required><br><br>

  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required><br><br>

  <input type="submit" value="Submit">
</form>

</body>
</html>
