package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.util.DBConnection;

@WebServlet("/closeAccount")
public class CloseAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountNo = Integer.parseInt(request.getParameter("accountNo"));

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Check account balance
            String checkBalanceSQL = "SELECT balance FROM Accounts WHERE account_no = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSQL)) {
                checkStmt.setInt(1, accountNo);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance != 0) {
                        conn.rollback();
                        response.sendRedirect("closeAccount.jsp?errorMessage=Unable to close account. Ensure the balance is zero.");
                        return;
                    }
                }
            }

            // Delete transactions first
            String deleteTransactionsSQL = "DELETE FROM Transactions WHERE account_no = ?";
            try (PreparedStatement deleteTransactionsStmt = conn.prepareStatement(deleteTransactionsSQL)) {
                deleteTransactionsStmt.setInt(1, accountNo);
                deleteTransactionsStmt.executeUpdate();
            }

            // Delete account
            String deleteAccountSQL = "DELETE FROM Accounts WHERE account_no = ?";
            try (PreparedStatement deleteAccountStmt = conn.prepareStatement(deleteAccountSQL)) {
                deleteAccountStmt.setInt(1, accountNo);
                deleteAccountStmt.executeUpdate();
            }

            conn.commit();
            response.sendRedirect("closeAccount.jsp?successMessage=Account closed successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error closing account");
        }
    }
}
