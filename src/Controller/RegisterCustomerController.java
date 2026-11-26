package Controller;

import DAO.AccountDAO;
import DAO.CustomerDAO;
import Entity.java.Company;
import Entity.java.EmployerDetails;
import Entity.java.Individual;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterCustomerController {
    @FXML private ComboBox<String> customerTypeCombo;
    @FXML private TextField firstNameField, surnameField, omangField, genderField, addressField;
    @FXML private TextField employerNameField, employerAddressField;
    @FXML private Button saveButton, backButton, logoutButton;
    @FXML private Label messageLabel;

    private Stage stage;
    private Scene operationsScene, openNewAccountScene;
    private OpenNewAccountController openNewAccountController;
    private CustomerDAO customerDAO = new CustomerDAO();
    private AccountDAO accountDAO = new AccountDAO();

    public RegisterCustomerController(Stage stage, Scene openNewAccountScene, OpenNewAccountController openNewAccountController) {
        this.stage = stage;
        this.openNewAccountScene = openNewAccountScene;
        this.openNewAccountController = openNewAccountController;
    }

    public void setOperationsScene(Scene operationsScene) {
        this.operationsScene = operationsScene;
    }

    @FXML
    public void initialize() {
        customerTypeCombo.getItems().addAll("Individual", "Company");
        customerTypeCombo.setValue("Individual");

        customerTypeCombo.setOnAction(e -> switchCustomerFields());
        saveButton.setOnAction(e -> createCustomer());
        backButton.setOnAction(e -> {
            clearForm();
            stage.setTitle("Customer Operations");
            stage.setScene(operationsScene);
        });
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void switchCustomerFields() {
        boolean isCompany = "Company".equals(customerTypeCombo.getValue());
        firstNameField.setPromptText(isCompany ? "Company Name" : "First Name");
        surnameField.setPromptText(isCompany ? "Registration No." : "Surname");
        omangField.setPromptText(isCompany ? "Tax ID" : "Omang ID (9 digits)");
        genderField.setDisable(isCompany);
        employerNameField.setDisable(isCompany);
        employerAddressField.setDisable(isCompany);
    }

    private void createCustomer() {
        try {
            String type = customerTypeCombo.getValue();
            String address = addressField.getText().trim();

            if ("Individual".equals(type)) {
                String first = firstNameField.getText().trim();
                String surname = surnameField.getText().trim();
                String omang = omangField.getText().trim();

                Individual ind = new Individual(omang, address, "client@bank.co.bw", first, surname, omang);
                if (accountDAO.getCustomerSummary(ind.getOmangID()) != null) {
                    showError("Error: Customer with Omang ID " + ind.getOmangID() + " already exists.");
                    return;
                }

                String empName = employerNameField.getText().trim();
                String empAddr = employerAddressField.getText().trim();
                EmployerDetails emp = (!empName.isEmpty() || !empAddr.isEmpty()) ? new EmployerDetails(empName, empAddr) : null;

                customerDAO.addCustomer(
                        ind.getOmangID(), type, ind.getPhysicalAddress(), ind.getEmailAddress(),
                        ind.getFirstName(), ind.getSurname(), ind.getOmangID(),
                        emp != null ? emp.getCompanyName() : "", "", "",
                        emp != null ? emp.getCompanyAddress() : ""
                );

                clearForm();
                openNewAccountController.prepareForCustomer(ind.getOmangID(), ind.getFirstName() + " " + ind.getSurname() + " (Omang: " + ind.getOmangID() + ")", true);
                stage.setTitle("Open New Account");
                stage.setScene(openNewAccountScene);
            } else {
                String compName = firstNameField.getText().trim();
                String regNum = surnameField.getText().trim();
                String taxId = omangField.getText().trim();

                Company comp = new Company(regNum, address, "corporate@bank.co.bw", compName, regNum, taxId);
                if (accountDAO.getCustomerSummary(comp.getRegNumber()) != null) {
                    showError("Error: Company with Registration No. " + comp.getRegNumber() + " already exists.");
                    return;
                }

                customerDAO.addCustomer(
                        comp.getRegNumber(), type, comp.getPhysicalAddress(), comp.getEmailAddress(),
                        "", "", "", comp.getCompanyName(), comp.getRegNumber(), comp.getTaxId(), ""
                );

                clearForm();
                openNewAccountController.prepareForCustomer(comp.getRegNumber(), comp.getCompanyName() + " (Reg: " + comp.getRegNumber() + ")", true);
                stage.setTitle("Open New Account");
                stage.setScene(openNewAccountScene);
            }
        } catch (IllegalArgumentException ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void clearForm() {
        firstNameField.clear();
        surnameField.clear();
        omangField.clear();
        genderField.clear();
        addressField.clear();
        employerNameField.clear();
        employerAddressField.clear();
        messageLabel.setText("");
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
