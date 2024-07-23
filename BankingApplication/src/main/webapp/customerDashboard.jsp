<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.bank.util.DBConnection" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
</head>
<body>
    <h2>Customer Dashboard</h2>
    <%
        int accountNo = (Integer) session.getAttribute("accountNo");
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Accounts WHERE account_no = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                out.println("<p>Account No: " + accountNo + "</p>");
                out.println("<p>Balance: $" + balance + "</p>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    %>
    
    
    <a href="viewTransactions.jsp">View Transactions</a><br><br>
    <a href="deposit.jsp">Deposit</a><br><br>
    <a href="withdraw.jsp">Withdraw</a><br><br>
    <a href="closeAccount.jsp">Close Account</a><br><br>
    <form action="LogoutServlet" method="post">
        <input type="submit" value="Logout">
    </form>
</body>
</html>