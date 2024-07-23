package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bank.model.Account;
import com.bank.util.DBConnection;

public class AccountDAO {

    public boolean addAccount(Account account) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Accounts (customer_id, initial_balance, balance) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, account.getCustomerId());
            stmt.setDouble(2, account.getInitialBalance());
            stmt.setDouble(3, account.getBalance());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int accountNo = rs.getInt(1);
                    account.setAccountNo(accountNo);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account getAccountByNo(int accountNo) {
        Account account = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Accounts WHERE account_no = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setAccountNo(rs.getInt("account_no"));
                account.setCustomerId(rs.getInt("customer_id"));
                account.setInitialBalance(rs.getDouble("initial_balance"));
                account.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public boolean updateAccountBalance(int accountNo, double balance) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Accounts SET balance = ? WHERE account_no = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, balance);
            stmt.setInt(2, accountNo);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccount1(int accountNo) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Accounts WHERE account_no = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNo);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isBalanceZero(int accountNo) {
        String checkBalanceSQL = "SELECT balance FROM Accounts WHERE account_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkBalanceSQL)) {
            stmt.setInt(1, accountNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance") == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete account
    public boolean deleteAccount(int accountNo) {
        String deleteAccountSQL = "DELETE FROM Accounts WHERE account_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteAccountSQL)) {
            stmt.setInt(1, accountNo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete transactions associated with the account
    public boolean deleteTransactions(int accountNo) {
        String deleteTransactionsSQL = "DELETE FROM Transactions WHERE account_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteTransactionsSQL)) {
            stmt.setInt(1, accountNo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
     
}