package DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnection {

    // Opens the connection to your bank.db file
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:bank.db");
    }

    // Reads schema.sql and creates your tables
    public static void createTables() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

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
