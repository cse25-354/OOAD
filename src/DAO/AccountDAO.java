package DAO;

import Entity.java.EmployerDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void addAccount(String accNumber, String custId, String type, double balance) {
        String sql = "INSERT INTO accounts VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNumber);
            stmt.setString(2, custId);
            stmt.setString(3, type);
            stmt.setDouble(4, balance);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error adding account: " + e.getMessage());
        }
    }

    public boolean customerHasAccountType(String custId, String accountType) {
        String sql = "SELECT 1 FROM accounts WHERE customer_id = ? AND account_type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, custId);
            stmt.setString(2, accountType);
            return stmt.executeQuery().next();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean accountNumberExists(String accNumber) {
        String sql = "SELECT 1 FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNumber);
            return stmt.executeQuery().next();
        } catch (Exception e) {
            return false;
        }
    }

    public EmployerDetails getCustomerEmployerOrCompany(String custId) {
        String sql = "SELECT customer_type, address, company_name, employer_address FROM customers WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, custId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("customer_type");
                String name = rs.getString("company_name");
                String addr = "Company".equals(type) ? rs.getString("address") : rs.getString("employer_address");
                if (name != null && !name.isEmpty() && addr != null && !addr.isEmpty()) {
                    return new EmployerDetails(name, addr);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading employer: " + e.getMessage());
        }
        return null;
    }

    public void updateEmployerDetails(String custId, String companyName, String employerAddress) {
        String sql = "UPDATE customers SET company_name = ?, employer_address = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, companyName);
            stmt.setString(2, employerAddress);
            stmt.setString(3, custId);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating employer: " + e.getMessage());
        }
    }

    public double getBalance(String accNumber) {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        } catch (Exception e) {
            System.out.println("Error reading balance: " + e.getMessage());
        }
        return 0.0;
    }

    public void updateBalance(String accNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accNumber);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

    public void deleteAccount(String accNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNumber);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }

    public String getCustomerSummary(String omangId) {
        String sql = "SELECT customer_type, first_name, surname, company_name FROM customers WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, omangId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if ("Individual".equals(rs.getString("customer_type"))) {
                    return rs.getString("first_name") + " " + rs.getString("surname") + " (Omang: " + omangId + ")";
                }
                return rs.getString("company_name") + " (Reg: " + omangId + ")";
            }
        } catch (Exception e) {
            System.out.println("Error finding customer: " + e.getMessage());
        }
        return null;
    }

    public List<String> getCustomerAccounts(String omangId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT account_number, account_type, balance FROM accounts WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, omangId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("account_number") + " - " + rs.getString("account_type")
                        + " - BWP " + String.format("%.2f", rs.getDouble("balance")));
            }
        } catch (Exception e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return list;
    }
}
