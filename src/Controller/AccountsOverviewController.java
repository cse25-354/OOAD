package Controller;

import DAO.AccountDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountsOverviewController {
    @FXML private Button backButton, logoutButton, searchButton;
    @FXML private Button openNewAccountButton, manageAccountButton, closeAccountButton;
    @FXML private TextField searchOmangField;
    @FXML private Label customerProfileLabel, messageLabel;
    @FXML private ListView<String> accountsListView;

    private Stage stage;
    private Scene operationsScene, openNewAccountScene, accountDetailsScene;
    private OpenNewAccountController openNewAccountController;
    private AccountDetailsController accountDetailsController;
    private AccountDAO accountDAO = new AccountDAO();

    private String activeOmangId;
    private String activeCustomerSummary;

    public AccountsOverviewController(Stage stage) {
        this.stage = stage;
    }

    public void setNavigation(Scene operationsScene, Scene openNewAccountScene, OpenNewAccountController openNewAccountController,
                              Scene accountDetailsScene, AccountDetailsController accountDetailsController) {
        this.operationsScene = operationsScene;
        this.openNewAccountScene = openNewAccountScene;
        this.openNewAccountController = openNewAccountController;
        this.accountDetailsScene = accountDetailsScene;
        this.accountDetailsController = accountDetailsController;
    }

    @FXML
    public void initialize() {
        backButton.setOnAction(e -> {
            stage.setTitle("Customer Operations");
            stage.setScene(operationsScene);
        });
        logoutButton.setOnAction(e -> handleLogout());
        searchButton.setOnAction(e -> handleSearch());
        openNewAccountButton.setOnAction(e -> handleOpenNewAccount());
        manageAccountButton.setOnAction(e -> handleManageSelected());
        closeAccountButton.setOnAction(e -> handleCloseAccount());
    }

    public void startSearchMode() {
        activeOmangId = null;
        activeCustomerSummary = null;
        searchOmangField.clear();
        customerProfileLabel.setText("No customer selected");
        accountsListView.getItems().clear();
        messageLabel.setText("");
    }

    public void loadCustomer(String omangId) {
        String summary = accountDAO.getCustomerSummary(omangId);
        if (summary == null) {
            showError("No customer found with ID: " + omangId);
            accountsListView.getItems().clear();
            return;
        }
        activeOmangId = omangId;
        activeCustomerSummary = summary;
        customerProfileLabel.setText(summary);
        accountsListView.getItems().setAll(accountDAO.getCustomerAccounts(omangId));
        messageLabel.setText("");
    }

    private void handleSearch() {
        String query = searchOmangField.getText().trim();
        if (query.isEmpty()) {
            showError("Enter a 9-digit Omang ID or Registration Number.");
            return;
        }
        loadCustomer(query);
    }

    private void handleOpenNewAccount() {
        if (activeOmangId == null) {
            showError("Search for a customer first.");
            return;
        }
        openNewAccountController.prepareForCustomer(activeOmangId, activeCustomerSummary, false);
        stage.setTitle("Open New Account");
        stage.setScene(openNewAccountScene);
    }

    private void handleManageSelected() {
        String selected = accountsListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select an account from the list first.");
            return;
        }
        String accNum = selected.substring(0, 10);
        String accType = selected.split(" - ")[1];
        accountDetailsController.loadAccount(activeOmangId, accNum, accType);
        stage.setTitle("Account Details & Transactions");
        stage.setScene(accountDetailsScene);
    }

    private void handleCloseAccount() {
        String selected = accountsListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select an account from the list first.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close " + selected + "?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Close Account?");
        confirm.setHeaderText(null);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            String accNum = selected.substring(0, 10);
            accountDAO.deleteAccount(accNum);
            loadCustomer(activeOmangId);
            showSuccess("Closed account: " + accNum);
        }
    }

    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/LoginView.fxml"));
            loader.setController(new LoginController(stage, operationsScene));
            stage.setTitle("Staff Login");
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            showError("Logout error: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setText(msg);
    }

    private void showSuccess(String msg) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(msg);
    }
}
