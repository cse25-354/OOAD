package Controller;

import DAO.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button signUpButton;
    @FXML private Label messageLabel;

    private Stage stage;
    private Scene customerScene;
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public LoginController(Stage stage, Scene customerScene) {
        this.stage = stage;
        this.customerScene = customerScene;
    }

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> handleLogin());
        signUpButton.setOnAction(e -> handleSignUp());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Please enter both username and password.");
            return;
        }

        if (employeeDAO.checkLogin(username, password)) {
            stage.setTitle("Customer & Employer Details");
            stage.setScene(customerScene);
        } else {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Invalid username or password. Try Sign up first.");
        }
    }

    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Please enter a username and password to sign up.");
            return;
        }

        // Check username format: TLR- followed by 4 digits (e.g., TLR-1024)
        if (!username.matches("TLR-\\d{4}")) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Username must follow format TLR-1234.");
            return;
        }

        // Check password format: exactly 4 numeric digits (e.g., 1234)
        if (!password.matches("\\d{4}")) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Password must be exactly 4 numbers (e.g., 1234).");
            return;
        }

        if (employeeDAO.registerEmployee(username, password)) {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Sign up successful! Click Login to continue.");
        } else {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Username already exists. Pick another one.");
        }
    }
}
