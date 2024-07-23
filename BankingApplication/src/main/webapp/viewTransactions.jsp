<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.bank.util.DBConnection" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Transactions</title>
</head>
<body>
    <h2>Last 10 Transactions</h2>
    <%
        int accountNo = (Integer) session.getAttribute("accountNo");
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Transactions WHERE account_no = ? ORDER BY transaction_date DESC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            out.println("<table border='1'>");
            out.println("<tr><th>Date</th><th>Type</th><th>Amount</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getTimestamp("transaction_date") + "</td>");
                out.println("<td>" + rs.getString("transaction_type") + "</td>");
                out.println("<td>" + rs.getDouble("amount") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
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
    <a href="customerDashboard.jsp">Back to Dashboard</a>
    <a href="DownloadPDFServlet">Download PDF</a><br>
    <
</body>
</html>