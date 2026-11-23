package Entity.java;

public class Company extends Customer {
    private String companyName;
    private String regNumber;
    private String taxId;

    public Company(String customerId, String physicalAddress, String emailAddress,
                   String companyName, String regNumber, String taxId) {
        super(customerId, physicalAddress, emailAddress);

        if (companyName == null || companyName.trim().isEmpty() || companyName.trim().length() > 50) {
            throw new IllegalArgumentException("Company name must be 1-50 characters.");
        }
        if (regNumber == null || regNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be empty.");
        }
        if (taxId == null || taxId.trim().isEmpty()) {
            throw new IllegalArgumentException("Tax ID cannot be empty.");
        }

        this.companyName = companyName.trim();
        this.regNumber = regNumber.trim();
        this.taxId = taxId.trim();
    }

    public boolean verifyTaxStatus() {
        return !taxId.isEmpty() && !regNumber.isEmpty();
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getTaxId() {
        return taxId;
    }
}
