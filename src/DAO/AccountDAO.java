package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

    // CREATE: Save a new account
    public void addAccount(String accNumber, String custId, String type, double balance) {
        String sql = "INSERT INTO accounts VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accNumber);
            stmt.setString(2, custId);
            stmt.setString(3, type);
            stmt.setDouble(4, balance);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error adding account: " + e.getMessage());
        }
    }

    // READ: Get current balance
    public double getBalance(String accNumber) {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accNumber);
            ResultSet rs = stmt.executeQuery();

            double balance = 0.0;
            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
            rs.close();
            stmt.close();
            conn.close();
            return balance;
        } catch (Exception e) {
            System.out.println("Error reading balance: " + e.getMessage());
            return 0.0;
        }
    }

    // UPDATE: Change account balance
    public void updateBalance(String accNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accNumber);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

    // DELETE: Remove an account
    public void deleteAccount(String accNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accNumber);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }
}
