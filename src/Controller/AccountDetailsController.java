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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AccountDetailsController {
    @FXML private Button backButton, logoutButton, depositButton, withdrawButton, interestButton;
    @FXML private Label accountHeaderLabel, balanceLabel, messageLabel;
    @FXML private TextField amountField;
    @FXML private ListView<String> transactionsListView;

    private Stage stage;
    private Scene operationsScene, accountsOverviewScene;
    private AccountsOverviewController overviewController;
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    private String activeOmangId, activeAccNum, activeAccType;

    public AccountDetailsController(Stage stage) {
        this.stage = stage;
    }

    public void setNavigation(Scene operationsScene, Scene accountsOverviewScene, AccountsOverviewController overviewController) {
        this.operationsScene = operationsScene;
        this.accountsOverviewScene = accountsOverviewScene;
        this.overviewController = overviewController;
    }

    @FXML
    public void initialize() {
        backButton.setOnAction(e -> {
            overviewController.loadCustomer(activeOmangId);
            stage.setTitle("Accounts Overview");
            stage.setScene(accountsOverviewScene);
        });
        logoutButton.setOnAction(e -> handleLogout());
        depositButton.setOnAction(e -> handleDeposit());
        withdrawButton.setOnAction(e -> handleWithdraw());
        interestButton.setOnAction(e -> handleInterest());
    }

    public void loadAccount(String omangId, String accNum, String accType) {
        activeOmangId = omangId;
        activeAccNum = accNum;
        activeAccType = accType;
        accountHeaderLabel.setText(accNum + " - " + accType);
        refreshView();
        messageLabel.setText("");
    }

    private void refreshView() {
        double bal = accountDAO.getBalance(activeAccNum);
        balanceLabel.setText("BWP " + String.format("%.2f", bal));
        transactionsListView.getItems().setAll(transactionDAO.getTransactionsByAccount(activeAccNum));
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            Account acc = buildEntity();
            acc.deposit(amount);
            accountDAO.updateBalance(activeAccNum, acc.getbalance());
            recordTxn("DEPOSIT", amount);
            amountField.clear();
            refreshView();
            showSuccess("Deposited BWP " + String.format("%.2f", amount));
        } catch (NumberFormatException e) {
            showError("Enter a valid numeric amount.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            Account acc = buildEntity();
            acc.withdraw(amount);
            accountDAO.updateBalance(activeAccNum, acc.getbalance());
            recordTxn("WITHDRAWAL", amount);
            amountField.clear();
            refreshView();
            showSuccess("Withdrew BWP " + String.format("%.2f", amount));
        } catch (NumberFormatException e) {
            showError("Enter a valid numeric amount.");
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            showError(e.getMessage());
        }
    }

    private void handleInterest() {
        if ("Cheque Account".equals(activeAccType)) {
            showError("Cheque Accounts do not earn monthly interest.");
            return;
        }
        Account acc = buildEntity();
        acc.MonthlyInterest();
        accountDAO.updateBalance(activeAccNum, acc.getbalance());
        refreshView();
        showSuccess("5% Monthly interest applied!");
    }

    private Account buildEntity() {
        double bal = accountDAO.getBalance(activeAccNum);
        if ("Savings Account".equals(activeAccType)) return new SavingsAccount(activeAccNum, bal);
        if ("Investment Account".equals(activeAccType)) return new InvestmentAccount(activeAccNum, bal);
        EmployerDetails emp = accountDAO.getCustomerEmployerOrCompany(activeOmangId);
        String name = (emp != null) ? emp.getCompanyName() : "Employer";
        String addr = (emp != null) ? emp.getCompanyAddress() : "Address";
        return new ChequeAccount(activeAccNum, bal, name, addr);
    }

    private void recordTxn(String type, double amount) {
        Transaction txn = new Transaction("TXN-" + System.currentTimeMillis(), type, amount);
        transactionDAO.addTransaction(txn.getTransactionId(), activeAccNum, txn.getType(), txn.getAmount(), LocalDate.now().toString());
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
