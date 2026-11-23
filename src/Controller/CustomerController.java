package Controller;

import Boundary.AccountView;
import Boundary.CustomerView;
import DAO.CustomerDAO;
import Entity.java.Company;
import Entity.java.EmployerDetails;
import Entity.java.Individual;
import javafx.stage.Stage;

public class CustomerController {
    private CustomerView view;
    private Stage stage;
    private AccountView accountView;
    private CustomerDAO customerDAO = new CustomerDAO();

    private Individual individual;
    private Company company;
    private EmployerDetails employer;
    private String currentCustomerId = "UNKNOWN";

    public CustomerController(CustomerView view, Stage stage, AccountView accountView) {
        this.view = view;
        this.stage = stage;
        this.accountView = accountView;

        this.view.getSaveButton().setOnAction(e -> createCustomer());
        this.view.getUpdateButton().setOnAction(e -> updateContact());
        this.view.getNextButton().setOnAction(e -> goToAccounts());
    }

    private void createCustomer() {
        try {
            String type = view.getCustomerType();
            String id = view.getCustomerId().trim();
            String address = view.getAddress().trim();
            String email = view.getEmail().trim();

            if (id.isEmpty() || address.isEmpty() || !email.contains("@")) {
                view.setDetailsDisplay("Error: Enter a valid ID, address, and email.");
                return;
            }

            if (type.equals("Individual")) {
                String first = view.getFirstName().trim();
                String surname = view.getSurname().trim();
                String omang = view.getOmang().trim();

                if (first.isEmpty() || surname.isEmpty() || !omang.matches("[0-9]{9}")) {
                    view.setDetailsDisplay("Error: Enter first name, surname, and 9-digit Omang.");
                    return;
                }

                individual = new Individual(id, address, email, first, surname, omang);
                currentCustomerId = id;

                String empName = view.getCompanyName().trim();
                String empAddr = view.getCompanyAddress().trim();
                if (!empName.isEmpty() && !empAddr.isEmpty()) {
                    employer = new EmployerDetails(empName, empAddr);
                }

                // Save Individual to SQLite database
                customerDAO.addCustomer(id, type, address, email, first, surname, omang, empName, "", "", empAddr);

                view.setDetailsDisplay("Saved Individual: " + individual.getFirstName() + " " + individual.getSurname());
            } else {
                String compName = view.getCompanyName().trim();
                String regNum = view.getRegNumber().trim();
                String taxId = view.getTaxId().trim();

                if (compName.isEmpty() || regNum.isEmpty() || taxId.isEmpty()) {
                    view.setDetailsDisplay("Error: Enter company name, registration number, and tax ID.");
                    return;
                }

                company = new Company(id, address, email, compName, regNum, taxId);
                currentCustomerId = id;

                // Save Company to SQLite database
                customerDAO.addCustomer(id, type, address, email, "", "", "", compName, regNum, taxId, "");

                view.setDetailsDisplay("Saved Company: " + company.getCompanyName());
            }
        } catch (IllegalArgumentException ex) {
            view.setDetailsDisplay("Error: " + ex.getMessage());
        }
    }

    private void updateContact() {
        String id = view.getCustomerId().trim();
        String newAddress = view.getAddress().trim();
        String newEmail = view.getEmail().trim();

        if (individual == null && company == null) {
            view.setDetailsDisplay("Error: Create a customer first.");
            return;
        }
        if (newAddress.isEmpty() || !newEmail.contains("@")) {
            view.setDetailsDisplay("Error: Enter a valid new address and email.");
            return;
        }

        if (individual != null) individual.updateContactInfo(newAddress, newEmail);
        if (company != null) company.updateContactInfo(newAddress, newEmail);

        // Update contact in SQLite database
        customerDAO.updateContact(id, newAddress, newEmail);

        view.setDetailsDisplay("Contact details updated!");
    }

    private void goToAccounts() {
        stage.setTitle("Account Management");
        stage.setScene(accountView.getScene());
    }

    public EmployerDetails getEmployer() {
        return employer;
    }

    public String getCurrentCustomerId() {
        return currentCustomerId;
    }
}
