<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Customer</title>
</head>
<body>
    <h1>Add New Customer</h1>
    <form action="" method="post">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required><br>

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required><br>

        <label for="mobileNo">Mobile No:</label>
        <input type="text" id="mobileNo" name="mobileNo" required><br>

        <label for="emailId">Email ID:</label>
        <input type="email" id="emailId" name="emailId" required><br>

        <label for="accountType">Account Type:</label>
        <select id="accountType" name="accountType">
            <option value="Saving">Saving Account</option>
            <option value="Current">Current Account</option>
        </select><br>

        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" required><br>

        <label for="idProof">ID Proof:</label>
        <input type="text" id="idProof" name="idProof" required><br>

        <label for="initialBalance">Initial Balance:</label>
        <input type="number" id="initialBalance" name="initialBalance" required><br>

        <input type="submit" value="Add Customer">
    </form>
</body>
</html>