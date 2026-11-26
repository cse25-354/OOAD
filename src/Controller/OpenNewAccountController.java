package Controller;

import DAO.AccountDAO;
import DAO.TransactionDAO;
import Entity.java.Account;
import Entity.java.ChequeAccount;
import Entity.java.EmployerDetails;
import Entity.java.InvestmentAccount;
import Entity.java.SavingsAccount;
import Entity.java.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class OpenNewAccountController {
    @FXML private Button backButton, logoutButton, createAccountButton, cancelButton;
    @FXML private Label customerBannerLabel, subTitleLabel, depositLabel, messageLabel;
    @FXML private ComboBox<String> accountTypeBox;
    @FXML private TextField accountNumberField, initialDepositField;
    @FXML private TextField employerNameField, employerAddressField;
    @FXML private VBox depositSection, employerSection;

    private Stage stage;
    private Scene operationsScene, accountsOverviewScene;
    private AccountsOverviewController overviewController;
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    private String activeOmangId;
    private boolean mandatoryFirstAccount;

    public OpenNewAccountController(Stage stage) {
        this.stage = stage;
    }

    public void setNavigation(Scene operationsScene, Scene accountsOverviewScene, AccountsOverviewController overviewController) {
        this.operationsScene = operationsScene;
        this.accountsOverviewScene = accountsOverviewScene;
        this.overviewController = overviewController;
    }

    @FXML
    public void initialize() {
        accountTypeBox.getItems().addAll("Savings Account", "Cheque Account", "Investment Account");
        accountTypeBox.setValue("Savings Account");
        accountTypeBox.setOnAction(e -> updateFieldsForAccountType());

        backButton.setOnAction(e -> goBack());
        cancelButton.setOnAction(e -> goBack());
        createAccountButton.setOnAction(e -> handleCreate());
        logoutButton.setOnAction(e -> handleLogout());

        updateFieldsForAccountType();
    }

    public void prepareForCustomer(String omangId, String customerSummary, boolean mandatory) {
        activeOmangId = omangId;
        mandatoryFirstAccount = mandatory;
        customerBannerLabel.setText("Customer: " + customerSummary);
        subTitleLabel.setText(mandatory ? "MANDATORY: Open First Account" : "Account Type");
        backButton.setDisable(mandatory);
        cancelButton.setDisable(mandatory);

        accountTypeBox.setValue("Savings Account");
        accountNumberField.clear();
        initialDepositField.clear();
        employerNameField.clear();
        employerAddressField.clear();
        messageLabel.setText("");

        EmployerDetails savedEmp = accountDAO.getCustomerEmployerOrCompany(omangId);
        if (savedEmp != null) {
            employerNameField.setText(savedEmp.getCompanyName());
            employerAddressField.setText(savedEmp.getCompanyAddress());
        }

        updateFieldsForAccountType();
    }

    private void updateFieldsForAccountType() {
        String type = accountTypeBox.getValue();

        if ("Cheque Account".equals(type)) {
            depositSection.setVisible(false);
            depositSection.setManaged(false);
            employerSection.setVisible(true);
            employerSection.setManaged(true);
        } else if ("Savings Account".equals(type)) {
            employerSection.setVisible(false);
            employerSection.setManaged(false);
            depositSection.setVisible(true);
            depositSection.setManaged(true);
            depositSection.setDisable(true);
            depositSection.setOpacity(0.4);
            initialDepositField.clear();
            depositLabel.setText("Initial Deposit (Not required for Savings)");
            initialDepositField.setPromptText("Opens with BWP 0.00");
        } else {
            employerSection.setVisible(false);
            employerSection.setManaged(false);
            depositSection.setVisible(true);
            depositSection.setManaged(true);
            depositSection.setDisable(false);
            depositSection.setOpacity(1.0);
            depositLabel.setText("Initial Deposit (BWP) - Minimum BWP 500.00");
            initialDepositField.setPromptText("e.g. 500.00");
        }
    }

    private void handleCreate() {
        try {
            String type = accountTypeBox.getValue();
            String accNum = accountNumberField.getText().trim();

            if (accountDAO.customerHasAccountType(activeOmangId, type)) {
                showError("This customer already has a " + type + ".");
                return;
            }
            if (accountDAO.accountNumberExists(accNum)) {
                showError("Account number " + accNum + " already exists.");
                return;
            }

            Account newAccount;
            double amount = 0.0;

            if ("Cheque Account".equals(type)) {
                String empName = employerNameField.getText().trim();
                String empAddr = employerAddressField.getText().trim();
                EmployerDetails emp = new EmployerDetails(empName, empAddr);
                accountDAO.updateEmployerDetails(activeOmangId, emp.getCompanyName(), emp.getCompanyAddress());
                newAccount = new ChequeAccount(accNum, 0.0, emp.getCompanyName(), emp.getCompanyAddress());
            } else if ("Savings Account".equals(type)) {
                newAccount = new SavingsAccount(accNum, 0.0);
            } else {
                amount = Double.parseDouble(initialDepositField.getText().trim());
                newAccount = new InvestmentAccount(accNum, amount);
            }

            accountDAO.addAccount(accNum, activeOmangId, type, newAccount.getbalance());
            if (amount > 0) {
                Transaction txn = new Transaction("TXN-" + System.currentTimeMillis(), "DEPOSIT", amount);
                transactionDAO.addTransaction(txn.getTransactionId(), accNum, txn.getType(), txn.getAmount(), LocalDate.now().toString());
            }

            Alert popup = new Alert(Alert.AlertType.INFORMATION, "Account Successfully Opened!");
            popup.setHeaderText(null);
            popup.showAndWait();

            overviewController.loadCustomer(activeOmangId);
            stage.setTitle("Accounts Overview");
            stage.setScene(accountsOverviewScene);
        } catch (NumberFormatException e) {
            showError("Enter a valid numeric amount.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void goBack() {
        if (mandatoryFirstAccount) {
            showError("You must open at least 1 account first.");
            return;
        }
        overviewController.loadCustomer(activeOmangId);
        stage.setTitle("Accounts Overview");
        stage.setScene(accountsOverviewScene);
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
}
