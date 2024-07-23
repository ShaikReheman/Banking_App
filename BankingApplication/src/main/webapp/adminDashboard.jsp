<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.bank.dao.*, com.bank.model.*" %>
<%@ page import="java.sql.*, com.bank.util.DBConnection, com.bank.util.DBUtils" %>
<%
    

    // Fetch customer list
    CustomerDAO customerDAO = new CustomerDAO();
    List<Customer> customers = customerDAO.getAllCustomers();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Admin Dashboard</h1>
    <a href="registerCustomer.jsp">Add New Customer</a> | <a href="adminLogout.jsp">Logout</a>
    <h2>Customer List</h2>
    <table border="1">
        <tr>
            <th>Customer ID</th>
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
                <a href="viewCustomer.jsp?id=<%= customer.getCustomerId() %>">View</a> | 
                <a href="resetPassword.jsp?id=<%= customer.getCustomerId() %>">ResetPassword</a> |
                <a href="editCustomer.jsp?id=<%= customer.getCustomerId() %>">Edit</a> | 
                <a href="deleteCustomer?id=<%= customer.getCustomerId() %>" onclick="return confirm('Are you sure you want to delete this customer?');">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
        <%
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                conn = DBConnection.getConnection();
                String sql = "SELECT account_no, full_name, email FROM Customers";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    int accountNo = rs.getInt("account_no");
                    String fullName = rs.getString("full_name");
                    String email = rs.getString("email");
        %>
        <tr>
            <td><%= accountNo %></td>
            <td><%= fullName %></td>
            <td><%= email %></td>
            <td>
                <form action="DeleteCustomerServlet" method="post">
                    <input type="hidden" name="accountNo" value="<%= accountNo %>" />
                    <input type="submit" value="Delete" />
                </form>
            </td>
        </tr>
        <%
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtils.closeQuietly(conn, stmt, rs);
            }
        %>
    </table>
</body>
</html>