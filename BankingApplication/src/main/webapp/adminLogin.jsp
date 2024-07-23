<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
</head>
<body>
    <h2>Admin Login</h2>
    <form action="AdminLoginServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
    <%
        if(request.getParameter("error") != null) {
            out.println("<p style='color:red;'>" + request.getParameter("error") + "</p>");
        }
    %>
    <h2>Customer Login</h2>
    <form action="CustomerLoginServlet" method="post">
        <label for="accountNo">Account No:</label>
        <input type="number" id="accountNo" name="accountNo" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
    <%
        if(request.getParameter("error") != null) {
            out.println("<p style='color:red;'>" + request.getParameter("error") + "</p>");
        }
    %>
</body>
</html>