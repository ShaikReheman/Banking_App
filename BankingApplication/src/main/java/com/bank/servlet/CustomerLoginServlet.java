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
import javax.servlet.http.HttpSession;
import com.bank.util.DBConnection;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountNo = Integer.parseInt(request.getParameter("accountNo"));
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Login WHERE account_no = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tempPassword = rs.getString("password");
                if (tempPassword.equals(password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accountNo", accountNo);
                    response.sendRedirect("customerDashboard.jsp");
                } else {
                    response.sendRedirect("customerLogin.jsp?error=Invalid Account Number or Password");
                }
            } else {
                response.sendRedirect("customerLogin.jsp?error=Invalid Account Number or Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
