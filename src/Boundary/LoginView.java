package Boundary;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button submitButton;
    private Scene scene;

    public LoginView() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label titleLabel = new Label("Login");
        Label userLabel = new Label("Username:");
        usernameField = new TextField();

        Label passLabel = new Label("Password:");
        passwordField = new PasswordField();

        submitButton = new Button("Submit");

        grid.add(titleLabel, 0, 0);
        grid.add(userLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(submitButton, 1, 3);

        scene = new Scene(grid, 350, 220);
    }

    public Scene getScene() {
        return scene;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public Button getSubmitButton() {
        return submitButton;
    }
}
