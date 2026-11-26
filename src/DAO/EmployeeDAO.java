package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDAO {

    // SIGN UP: Saves a new bank employee
    public boolean registerEmployee(String username, String password) {
        String sql = "INSERT INTO employees (username, password) VALUES (?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("Sign up error: " + e.getMessage());
            return false;
        }
    }

    // LOGIN: Checks if the employee's username and password match
    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            boolean found = rs.next();
            rs.close();
            stmt.close();
            conn.close();
            return found;
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }
}
