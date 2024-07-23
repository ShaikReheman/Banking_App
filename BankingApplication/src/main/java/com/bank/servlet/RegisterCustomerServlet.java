package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.util.DBConnection;

@WebServlet("/RegisterCustomerServlet")
public class RegisterCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobileNo");
        String emailId = request.getParameter("emailId");
        String accountType = request.getParameter("accountType");
        double initialBalance = Double.parseDouble(request.getParameter("initialBalance"));
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("idProof");
        String tempPassword = generateTempPassword();
        

        try (Connection conn = DBConnection.getConnection()) {
            String sql1 = "INSERT INTO Customer (full_name, address, mobile_no, email_id, account_type, dob, id_proof) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt1 = conn.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, fullName);
            stmt1.setString(2, address);
            stmt1.setString(3, mobileNo);
            stmt1.setString(4, emailId);
            stmt1.setString(5, accountType);
            stmt1.setString(6, dob);
            stmt1.setString(7, idProof);
            stmt1.executeUpdate();
            
            ResultSet rs = stmt1.getGeneratedKeys();
            if (rs.next()) {
                int customerId = rs.getInt(1);
                
                String sql2 = "INSERT INTO Accounts (customer_id, initial_balance, balance) VALUES (?, ?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(sql2, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt2.setInt(1, customerId);
                stmt2.setDouble(2, initialBalance);
                stmt2.setDouble(3, initialBalance);
                stmt2.executeUpdate();
                
                ResultSet rs2 = stmt2.getGeneratedKeys();
                if (rs2.next()) {
                    int accountNo = rs2.getInt(1);
                    
                    String sql3 = "INSERT INTO Login (customer_id, account_no, password) VALUES (?, ?, ?)";
                    PreparedStatement stmt3 = conn.prepareStatement(sql3);
                    stmt3.setInt(1, customerId);
                    stmt3.setInt(2, accountNo);
                    stmt3.setString(3, tempPassword);
                    stmt3.executeUpdate();
                    
                    // Provide the generated account number and temporary password to the customer
                    request.setAttribute("accountNo", accountNo);
                    request.setAttribute("tempPassword", tempPassword);
                    request.getRequestDispatcher("registrationSuccess.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateTempPassword() {
        Random random = new Random();
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder tempPassword = new StringBuilder();
        for (int i = 0; i < length; i++) {
            tempPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return tempPassword.toString();
    }
}