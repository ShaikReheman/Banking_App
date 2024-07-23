<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register Customer</title>
</head>
<body>
    <h2>Register Customer</h2>
    <form action="RegisterCustomerServlet" method="post">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required><br><br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required><br><br>
        <label for="mobileNo">Mobile No:</label>
        <input type="text" id="mobileNo" name="mobileNo" required><br><br>
        <label for="emailId">Email ID:</label>
        <input type="email" id="emailId" name="emailId" required><br><br>
        <label for="accountType">Type of Account:</label>
        <select id="accountType" name="accountType" required>
            <option value="Saving">Saving</option>
            <option value="Current">Current</option>
        </select><br><br>
        <label for="initialBalance">Initial Balance:</label>
        <input type="number" id="initialBalance" name="initialBalance" min="1000" required><br><br>
        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" required><br><br>
        <label for="idProof">ID Proof:</label>
        <input type="text" id="idProof" name="idProof" required><br><br>
        <input type="submit" value="Register">
    </form>
</body>
</html>