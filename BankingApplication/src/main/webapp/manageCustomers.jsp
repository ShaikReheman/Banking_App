<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bank.model.Customer" %>
<%@ page import="com.bank.dao.CustomerDAO" %>
<%
    // Check if admin is logged in
    
    CustomerDAO customerDAO = new CustomerDAO();
    List<Customer> customers = customerDAO.getAllCustomers();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Customers</title>
    <link rel="stylesheet" href="path/to/your/css/style.css">
</head>
<body>
    <h1>Manage Customers</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Address</th>
            <th>Mobile No</th>
            <th>Email ID</th>
            <th>Account Type</th>
            <th>Date of Birth</th>
            <th>ID Proof</th>
            <th>Actions</th>
        </tr>
        <%
            for (Customer customer : customers) {
        %>
        <tr>
            <td><%= customer.getCustomerId() %></td>
            <td><%= customer.getFullName() %></td>
            <td><%= customer.getAddress() %></td>
            <td><%= customer.getMobileNo() %></td>
            <td><%= customer.getEmailId() %></td>
            <td><%= customer.getAccountType() %></td>
            <td><%= customer.getDob() %></td>
            <td><%= customer.getIdProof() %></td>
            <td>
                <a href="editCustomer.jsp?id=<%= customer.getCustomerId() %>">Edit</a> |
                <a href="deleteCustomer?id=<%= customer.getCustomerId() %>" onclick="return confirm('Are you sure you want to delete this customer?');">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <br>
    <a href="addCustomer.jsp">Add New Customer</a>
    <br><br>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>