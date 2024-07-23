package com.bank.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.CustomerDAO;
import com.bank.util.PasswordUtils;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");

        CustomerDAO customerDAO = new CustomerDAO();
        boolean isReset = customerDAO.resetPassword(accountNumber, currentPassword, newPassword);

        if (isReset) {
            response.sendRedirect("login.jsp?message=Password reset successfully");
        } else {
            response.sendRedirect("resetPassword.jsp?error=Invalid account number or password");
        }
    }
}
