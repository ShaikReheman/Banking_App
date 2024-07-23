<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <p><%= request.getParameter("message") %></p>
    <a href="index.jsp">Back to Home</a>
</body>
</html>
