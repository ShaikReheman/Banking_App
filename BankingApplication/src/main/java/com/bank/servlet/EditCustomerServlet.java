package com.bank.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

@WebServlet("/editCustomer")
public class EditCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("errorPage.jsp?message=Invalid customer ID");
            return;
        }

        int customerId;
        try {
            customerId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("errorPage.jsp?message=Invalid customer ID format");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerById(customerId);

        if (customer == null) {
            response.sendRedirect("errorPage.jsp?message=Customer not found");
            return;
        }

        request.setAttribute("customer", customer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("editCustomer.jsp");
        dispatcher.forward(request, response);
    }
}
