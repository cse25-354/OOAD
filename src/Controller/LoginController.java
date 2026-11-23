package Controller;

import Boundary.CustomerView;
import Boundary.LoginView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginController {
    private LoginView view;
    private Stage stage;
    private CustomerView customerView;

    public LoginController(LoginView view, Stage stage, CustomerView customerView) {
        this.view = view;
        this.stage = stage;
        this.customerView = customerView;

        this.view.getSubmitButton().setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = view.getUsername().trim();
        String password = view.getPassword().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both username and password.");
            return;
        }

        stage.setTitle("Customer & Employer Details");
        stage.setScene(customerView.getScene());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
