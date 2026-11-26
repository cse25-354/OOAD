package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void addTransaction(String transId, String accNumber, String type, double amount, String date) {
        String sql = "INSERT INTO transactions VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transId);
            stmt.setString(2, accNumber);
            stmt.setString(3, type);
            stmt.setDouble(4, amount);
            stmt.setString(5, date);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public List<String> getTransactionsByAccount(String accNumber) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT date, type, amount FROM transactions WHERE account_number = ? ORDER BY rowid DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String row = rs.getString("date") + "  |  " + rs.getString("type")
                        + "  |  BWP " + String.format("%.2f", rs.getDouble("amount"));
                list.add(row);
            }
        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return list;
    }
}
