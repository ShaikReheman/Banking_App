<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Deposit</title>
</head>
<body>
    <h2>Deposit</h2>
    <form action="DepositServlet" method="post">
        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount" min="1" required><br><br>
        <input type="submit" value="Deposit">
    </form>
</body>
</html>