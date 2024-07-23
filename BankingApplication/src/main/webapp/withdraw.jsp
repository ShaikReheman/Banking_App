<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Withdraw</title>
</head>
<body>
    <h2>Withdraw</h2>
    <form action="WithdrawServlet" method="post">
        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount" min="1" required><br><br>
        <input type="submit" value="Withdraw">
    </form>
    <%
        if(request.getParameter("error") != null) {
            out.println("<p style='color:red;'>" + request.getParameter("error") + "</p>");
        }
    %>
</body>
</html>