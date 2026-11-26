package DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:bank.db");
    }

    public static void createTables() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("PRAGMA table_info(customers)");
            int columnCount = 0;
            while (rs.next()) {
                columnCount++;
            }
            rs.close();

            if (columnCount > 0 && columnCount < 11) {
                stmt.execute("DROP TABLE customers");
            }

            Scanner reader = new Scanner(new File("schema.sql"));
            reader.useDelimiter(";");

            while (reader.hasNext()) {
                String sql = reader.next().trim();
                if (!sql.isEmpty()) {
                    stmt.execute(sql);
                }
            }

            reader.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
