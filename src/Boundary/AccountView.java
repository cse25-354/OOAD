package Boundary;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AccountView {
    private ComboBox<String> accountTypeBox;
    private Button searchButton, createAccountButton;
    private TextField accountNumberField, balanceField, amountField;
    private Button depositButton, withdrawButton;
    private TextArea transactionsArea;
    private Label messageLabel;
    private Scene scene;

    public AccountView() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        GridPane topPane = new GridPane();
        topPane.setHgap(10);
        topPane.setVgap(10);
        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("Investment Account", "Savings Account", "Cheque Account");
        accountTypeBox.setValue("Investment Account");
        searchButton = new Button("Search");
        createAccountButton = new Button("Create Account");

        topPane.add(new Label("Account:"), 0, 0);
        topPane.add(accountTypeBox, 1, 0);
        topPane.add(new HBox(10, searchButton, createAccountButton), 1, 1);

        GridPane detailsPane = new GridPane();
        detailsPane.setHgap(10);
        detailsPane.setVgap(10);
        accountNumberField = new TextField();
        balanceField = new TextField();
        balanceField.setEditable(false);

        detailsPane.add(new Label("Account Number:"), 0, 0);
        detailsPane.add(accountNumberField, 1, 0);
        detailsPane.add(new Label("Balance (BWP):"), 0, 1);
        detailsPane.add(balanceField, 1, 1);

        GridPane operationsPane = new GridPane();
        operationsPane.setHgap(10);
        operationsPane.setVgap(10);
        amountField = new TextField();
        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");

        operationsPane.add(new Label("Operations"), 0, 0);
        operationsPane.add(new Label("Amount:"), 0, 1);
        operationsPane.add(amountField, 1, 1);
        operationsPane.add(new HBox(10, depositButton, withdrawButton), 1, 2);

        transactionsArea = new TextArea();
        transactionsArea.setEditable(false);
        transactionsArea.setPrefRowCount(4);
        messageLabel = new Label();

        mainLayout.getChildren().addAll(
                topPane, detailsPane, operationsPane,
                new Label("Transactions:"), transactionsArea, messageLabel
        );
        scene = new Scene(mainLayout, 430, 520);
    }

    public Scene getScene() { return scene; }
    public String getSelectedAccountType() { return accountTypeBox.getValue(); }
    public String getAccountNumber() { return accountNumberField.getText(); }
    public String getAmount() { return amountField.getText(); }
    public Button getSearchButton() { return searchButton; }
    public Button getCreateAccountButton() { return createAccountButton; }
    public Button getDepositButton() { return depositButton; }
    public Button getWithdrawButton() { return withdrawButton; }

    public void setAccountNumber(String accNum) { accountNumberField.setText(accNum); }
    public void setBalance(String balance) { balanceField.setText(balance); }
    public void setTransactions(String history) { transactionsArea.setText(history); }
    public void setMessage(String msg) { messageLabel.setText(msg); }
}



