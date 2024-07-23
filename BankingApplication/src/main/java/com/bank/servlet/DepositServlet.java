package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.util.DBConnection;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountNo = (Integer) request.getSession().getAttribute("accountNo");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql1 = "UPDATE Accounts SET balance = balance + ? WHERE account_no = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setDouble(1, amount);
            stmt1.setInt(2, accountNo);
            stmt1.executeUpdate();

            String sql2 = "INSERT INTO Transactions (account_no, amount, transaction_type) VALUES (?, ?, 'Deposit')";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setInt(1, accountNo);
            stmt2.setDouble(2, amount);
            stmt2.executeUpdate();

            response.sendRedirect("customerDashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}