package Boundary;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CustomerView {
    private ComboBox<String> customerTypeBox;
    private TextField customerIdField, addressField, emailField;
    private TextField firstNameField, surnameField, omangField;
    private TextField companyNameField, regNumberField, taxIdField, companyAddressField;
    private Button saveButton, updateButton, nextButton;
    private TextArea detailsDisplay;
    private Scene scene;

    public CustomerView() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(15));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);

        customerTypeBox = new ComboBox<>();
        customerTypeBox.getItems().addAll("Individual", "Company");
        customerTypeBox.setValue("Individual");

        customerIdField = new TextField();
        addressField = new TextField();
        emailField = new TextField();
        firstNameField = new TextField();
        surnameField = new TextField();
        omangField = new TextField();
        companyNameField = new TextField();
        regNumberField = new TextField();
        taxIdField = new TextField();
        companyAddressField = new TextField();

        form.add(new Label("Customer Type:"), 0, 0); form.add(customerTypeBox, 1, 0);
        form.add(new Label("Customer ID:"), 0, 1);   form.add(customerIdField, 1, 1);
        form.add(new Label("Address:"), 0, 2);       form.add(addressField, 1, 2);
        form.add(new Label("Email:"), 0, 3);         form.add(emailField, 1, 3);
        form.add(new Label("First Name:"), 0, 4);    form.add(firstNameField, 1, 4);
        form.add(new Label("Surname:"), 0, 5);       form.add(surnameField, 1, 5);
        form.add(new Label("Omang (9 digits):"), 0, 6); form.add(omangField, 1, 6);
        form.add(new Label("Company / Employer Name:"), 0, 7); form.add(companyNameField, 1, 7);
        form.add(new Label("Reg Number:"), 0, 8);    form.add(regNumberField, 1, 8);
        form.add(new Label("Tax ID:"), 0, 9);        form.add(taxIdField, 1, 9);
        form.add(new Label("Employer Address:"), 0, 10); form.add(companyAddressField, 1, 10);

        saveButton = new Button("Create Customer");
        updateButton = new Button("Update Contact");
        nextButton = new Button("Go to Accounts");
        detailsDisplay = new TextArea();
        detailsDisplay.setEditable(false);
        detailsDisplay.setPrefRowCount(3);

        root.getChildren().addAll(new Label("Customer & Employer Details"), form, saveButton, updateButton, nextButton, detailsDisplay);
        scene = new Scene(root, 450, 560);
    }

    public Scene getScene() { return scene; }
    public String getCustomerType() { return customerTypeBox.getValue(); }
    public String getCustomerId() { return customerIdField.getText(); }
    public String getAddress() { return addressField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getFirstName() { return firstNameField.getText(); }
    public String getSurname() { return surnameField.getText(); }
    public String getOmang() { return omangField.getText(); }
    public String getCompanyName() { return companyNameField.getText(); }
    public String getRegNumber() { return regNumberField.getText(); }
    public String getTaxId() { return taxIdField.getText(); }
    public String getCompanyAddress() { return companyAddressField.getText(); }
    public Button getSaveButton() { return saveButton; }
    public Button getUpdateButton() { return updateButton; }
    public Button getNextButton() { return nextButton; }
    public void setDetailsDisplay(String text) { detailsDisplay.setText(text); }
}
