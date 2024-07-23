package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.util.DBConnection;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountNo = (Integer) request.getSession().getAttribute("accountNo");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql1 = "SELECT balance FROM Accounts WHERE account_no = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setInt(1, accountNo);
            ResultSet rs = stmt1.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String sql2 = "UPDATE Accounts SET balance = balance - ? WHERE account_no = ?";
                    PreparedStatement stmt2 = conn.prepareStatement(sql2);
                    stmt2.setDouble(1, amount);
                    stmt2.setInt(2, accountNo);
                    stmt2.executeUpdate();

                    String sql3 = "INSERT INTO Transactions (account_no, amount, transaction_type) VALUES (?, ?, 'Withdraw')";
                    PreparedStatement stmt3 = conn.prepareStatement(sql3);
                    stmt3.setInt(1, accountNo);
                    stmt3.setDouble(2, amount);
                    stmt3.executeUpdate();

                    response.sendRedirect("customerDashboard.jsp");
                } else {
                    response.sendRedirect("withdraw.jsp?error=Insufficient Balance");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}