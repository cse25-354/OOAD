package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerOperationsController {
    @FXML private Button createCustomerButton, searchCustomerButton, logoutButton;

    private Stage stage;
    private Scene registerCustomerScene, accountsOverviewScene, operationsScene;
    private AccountsOverviewController overviewController;

    public CustomerOperationsController(Stage stage, Scene registerCustomerScene, Scene accountsOverviewScene, AccountsOverviewController overviewController) {
        this.stage = stage;
        this.registerCustomerScene = registerCustomerScene;
        this.accountsOverviewScene = accountsOverviewScene;
        this.overviewController = overviewController;
    }

    public void setOperationsScene(Scene operationsScene) {
        this.operationsScene = operationsScene;
    }

    @FXML
    public void initialize() {
        createCustomerButton.setOnAction(e -> {
            stage.setTitle("Register New Customer");
            stage.setScene(registerCustomerScene);
        });
        searchCustomerButton.setOnAction(e -> {
            overviewController.startSearchMode();
            stage.setTitle("Accounts Overview");
            stage.setScene(accountsOverviewScene);
        });
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/LoginView.fxml"));
            loader.setController(new LoginController(stage, operationsScene));
            stage.setTitle("Staff Login");
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            System.out.println("Logout error: " + e.getMessage());
        }
    }
}
