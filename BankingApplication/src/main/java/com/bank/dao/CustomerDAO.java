package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.Customer;
import com.bank.util.DBConnection;
import com.bank.util.PasswordUtils;

public class CustomerDAO {

    public boolean addCustomer(Customer customer) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Customer (full_name, address, mobile_no, email_id, account_type, dob, id_proof) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getMobileNo());
            stmt.setString(4, customer.getEmailId());
            stmt.setString(5, customer.getAccountType());
            stmt.setDate(6, customer.getDob());
            stmt.setString(7, customer.getIdProof());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int customerId = rs.getInt(1);
                    customer.setCustomerId(customerId);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE customer_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setMobileNo(rs.getString("mobile_no"));
                customer.setEmailId(rs.getString("email_id"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setDob(rs.getDate("dob"));
                customer.setIdProof(rs.getString("id_proof"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean updateCustomer(Customer customer) {
        String updateCustomerSQL = "UPDATE Customer SET full_name = ?, address = ?, mobile_no = ?, email_id = ?, account_type = ?, dob = ?, id_proof = ? WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateCustomerSQL)) {

            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getMobileNo());
            stmt.setString(4, customer.getEmailId());
            stmt.setString(5, customer.getAccountType());
            stmt.setDate(6, customer.getDob());
            stmt.setString(7, customer.getIdProof());
            stmt.setInt(8, customer.getCustomerId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM Customer";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setMobileNo(rs.getString("mobile_no"));
                customer.setEmailId(rs.getString("email_id"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setDob(rs.getDate("dob"));
                customer.setIdProof(rs.getString("id_proof"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    

    public boolean deleteCustomer(String customerId) {
        boolean isDeleted = false;
        String deleteCustomerSQL = "DELETE FROM Customer WHERE customer_id = ?";
        String deleteAccountSQL = "DELETE FROM Accounts WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerSQL);
                 PreparedStatement deleteAccountStmt = conn.prepareStatement(deleteAccountSQL)) {

                // Delete account associated with the customer
                deleteAccountStmt.setInt(1, Integer.parseInt(customerId));
                deleteAccountStmt.executeUpdate();

                // Delete customer
                deleteCustomerStmt.setInt(1, Integer.parseInt(customerId));
                int rowsAffected = deleteCustomerStmt.executeUpdate();

                if (rowsAffected > 0) {
                    conn.commit();
                    isDeleted = true;
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    public boolean resetPassword(String accountNumber, String currentPassword, String newPassword) {
        boolean isReset = false;
        String getPasswordSQL = "SELECT password FROM Login WHERE account_no = ?";
        String updatePasswordSQL = "UPDATE Login SET password = ? WHERE account_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement getPasswordStmt = conn.prepareStatement(getPasswordSQL);
             PreparedStatement updatePasswordStmt = conn.prepareStatement(updatePasswordSQL)) {

            getPasswordStmt.setInt(1, Integer.parseInt(accountNumber));
            ResultSet rs = getPasswordStmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (PasswordUtils.checkPassword(currentPassword, storedPassword)) {
                    String hashedNewPassword = PasswordUtils.hashPassword(newPassword);
                    updatePasswordStmt.setString(1, hashedNewPassword);
                    updatePasswordStmt.setInt(2, Integer.parseInt(accountNumber));
                    int rowsAffected = updatePasswordStmt.executeUpdate();
                    isReset = rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isReset;
    }
    public boolean closeAccount(int accountNo) {
        boolean isClosed = false;
        try (Connection conn = DBConnection.getConnection()) {
            // Check the account balance
            String sqlCheckBalance = "SELECT balance FROM Accounts WHERE account_no = ?";
            PreparedStatement stmtCheck = conn.prepareStatement(sqlCheckBalance);
            stmtCheck.setInt(1, accountNo);
            ResultSet rs = stmtCheck.executeQuery();
            
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                // Only close account if balance is zero
                if (balance == 0) {
                    // Delete account
                    String sqlDeleteAccount = "DELETE FROM Accounts WHERE account_no = ?";
                    PreparedStatement stmtDelete = conn.prepareStatement(sqlDeleteAccount);
                    stmtDelete.setInt(1, accountNo);
                    int rowsAffected = stmtDelete.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        isClosed = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isClosed;
    }
}