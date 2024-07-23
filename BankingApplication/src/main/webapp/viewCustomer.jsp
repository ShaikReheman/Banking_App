<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bank.dao.CustomerDAO, com.bank.model.Customer" %>
<%
    int customerId = Integer.parseInt(request.getParameter("id"));
    CustomerDAO customerDAO = new CustomerDAO();
    Customer customer = customerDAO.getCustomerById(customerId);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Customer</title>
</head>
<body>
    <h1>Customer Details</h1>
    <p><strong>Customer ID:</strong> <%= customer.getCustomerId() %></p>
    <p><strong>Full Name:</strong> <%= customer.getFullName() %></p>
    <p><strong>Address:</strong> <%= customer.getAddress() %></p>
    <p><strong>Mobile No:</strong> <%= customer.getMobileNo() %></p>
    <p><strong>Email ID:</strong> <%= customer.getEmailId() %></p>
    <p><strong>Account Type:</strong> <%= customer.getAccountType() %></p>
    <p><strong>Date of Birth:</strong> <%= customer.getDob() %></p>
    <p><strong>ID Proof:</strong> <%= customer.getIdProof() %></p>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>