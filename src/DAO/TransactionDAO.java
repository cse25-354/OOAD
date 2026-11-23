package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransactionDAO {

    // CREATE: Save a deposit or withdrawal record
    public void addTransaction(String transId, String accNumber, String type, double amount, String date) {
        String sql = "INSERT INTO transactions VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, transId);
            stmt.setString(2, accNumber);
            stmt.setString(3, type);
            stmt.setDouble(4, amount);
            stmt.setString(5, date);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
}
