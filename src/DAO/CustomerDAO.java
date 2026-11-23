package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerDAO {

    // CREATE: Saves a new customer from your screen
    public void addCustomer(String id, String type, String address, String email,
                            String firstName, String surname, String omang,
                            String companyName, String regNumber, String taxId,
                            String employerAddress) {
        String sql = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, type);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, firstName);
            stmt.setString(6, surname);
            stmt.setString(7, omang);
            stmt.setString(8, companyName);
            stmt.setString(9, regNumber);
            stmt.setString(10, taxId);
            stmt.setString(11, employerAddress);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            System.out.println("Customer saved to database!");
        } catch (Exception e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }
    }

    // UPDATE: Updates address and email when you click "Update Contact"
    public void updateContact(String id, String address, String email) {
        String sql = "UPDATE customers SET address = ?, email = ? WHERE customer_id = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, address);
            stmt.setString(2, email);
            stmt.setString(3, id);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            System.out.println("Contact updated in database!");
        } catch (Exception e) {
            System.out.println("Error updating contact: " + e.getMessage());
        }
    }
}
