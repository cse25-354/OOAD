package Controller;

import Boundary.AccountView;
import DAO.CustomerDAO;
import Entity.java.Company;
import Entity.java.EmployerDetails;
import Entity.java.Individual;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerController {
    @FXML private ComboBox<String> customerTypeCombo;
    @FXML private TextField firstNameField;
    @FXML private TextField surnameField;
    @FXML private TextField omangField;
    @FXML private TextField genderField;
    @FXML private TextField addressField;
    @FXML private TextField employerNameField;
    @FXML private TextField employerAddressField;
    @FXML private Button saveButton;
    @FXML private Button goToAccountsButton;
    @FXML private Button logoutButton;
    @FXML private Label messageLabel;

    private Stage stage;
    private AccountView accountView;
    private CustomerDAO customerDAO = new CustomerDAO();

    private Individual individual;
    private Company company;
    private EmployerDetails employer;
    private String currentCustomerId = "UNKNOWN";

    public CustomerController(Stage stage, AccountView accountView) {
        this.stage = stage;
        this.accountView = accountView;
    }

    @FXML
    public void initialize() {
        customerTypeCombo.getItems().addAll("Individual", "Company");
        customerTypeCombo.setValue("Individual");

        customerTypeCombo.setOnAction(e -> switchCustomerFields());
        saveButton.setOnAction(e -> createCustomer());
        goToAccountsButton.setOnAction(e -> goToAccounts());
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void switchCustomerFields() {
        boolean isCompany = "Company".equals(customerTypeCombo.getValue());
        if (isCompany) {
            firstNameField.setPromptText("Company Name");
            surnameField.setPromptText("Registration No.");
            omangField.setPromptText("Tax ID");
            genderField.setDisable(true);
            genderField.clear();
            employerNameField.setDisable(true);
            employerAddressField.setDisable(true);
        } else {
            firstNameField.setPromptText("First Name");
            surnameField.setPromptText("Surname");
            omangField.setPromptText("Omang ID");
            genderField.setDisable(false);
            employerNameField.setDisable(false);
            employerAddressField.setDisable(false);
        }
    }

    private void createCustomer() {
        try {
            String type = customerTypeCombo.getValue();
            String address = addressField.getText().trim();

            if (address.isEmpty()) {
                showError("Error: Please enter an address or phone number.");
                return;
            }

            if ("Individual".equals(type)) {
                String first = firstNameField.getText().trim();
                String surname = surnameField.getText().trim();
                String omang = omangField.getText().trim();

                if (first.isEmpty() || surname.isEmpty() || !omang.matches("[0-9]{9}")) {
                    showError("Error: Enter first name, surname, and 9-digit Omang ID.");
                    return;
                }

                String id = "CUST-" + omang;
                individual = new Individual(id, address, "client@bank.co.bw", first, surname, omang);
                currentCustomerId = id;

                String empName = employerNameField.getText().trim();
                String empAddr = employerAddressField.getText().trim();
                if (!empName.isEmpty() && !empAddr.isEmpty()) {
                    employer = new EmployerDetails(empName, empAddr);
                }

                customerDAO.addCustomer(id, type, address, "client@bank.co.bw", first, surname, omang, empName, "", "", empAddr);
                showSuccess("Saved Individual: " + first + " " + surname + " (" + id + ")");
            } else {
                String compName = firstNameField.getText().trim();
                String regNum = surnameField.getText().trim();
                String taxId = omangField.getText().trim();

                if (compName.isEmpty() || regNum.isEmpty() || taxId.isEmpty()) {
                    showError("Error: Enter company name, registration number, and tax ID.");
                    return;
                }

                String id = "COMP-" + regNum;
                company = new Company(id, address, "corporate@bank.co.bw", compName, regNum, taxId);
                currentCustomerId = id;

                customerDAO.addCustomer(id, type, address, "corporate@bank.co.bw", "", "", "", compName, regNum, taxId, "");
                showSuccess("Saved Company: " + compName + " (" + id + ")");
            }
        } catch (IllegalArgumentException ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void goToAccounts() {
        stage.setTitle("Account Management");
        stage.setScene(accountView.getScene());
    }

    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/LoginView.fxml"));
            FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/Boundary/CustomerView.fxml"));
            CustomerController freshCustomerController = new CustomerController(stage, accountView);
            customerLoader.setController(freshCustomerController);
            Parent customerRoot = customerLoader.load();

            LoginController loginController = new LoginController(stage, new Scene(customerRoot));
            loader.setController(loginController);
            Parent loginRoot = loader.load();

            stage.setTitle("Banking System - Login");
            stage.setScene(new Scene(loginRoot));
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

    public EmployerDetails getEmployer() {
        return employer;
    }

    public String getCurrentCustomerId() {
        return currentCustomerId;
    }
}
