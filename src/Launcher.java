import DAO.DatabaseConnection;

public class Launcher {
    public static void main(String[] args) {
        DatabaseConnection.createTables();
        BankingApp.main(args);
    }
}
