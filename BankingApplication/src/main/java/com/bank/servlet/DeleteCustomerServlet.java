package com.bank.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.CustomerDAO;

@WebServlet("/deleteCustomer")
public class DeleteCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("deleteCustomer.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        System.out.println("Attempting to delete customer with ID: " + customerId);  // Logging

        CustomerDAO customerDAO = new CustomerDAO();
        boolean isDeleted = customerDAO.deleteCustomer(customerId);

        if (isDeleted) {
            response.sendRedirect("deleteCustomer.jsp?successMessage=Customer deleted successfully.");
        } else {
            response.sendRedirect("deleteCustomer.jsp?errorMessage=Failed to delete customer. Please try again.");
        }
    }
}
