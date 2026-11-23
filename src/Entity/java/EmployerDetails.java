package Entity.java;

public class EmployerDetails {
    private String companyName;
    private String companyAddress;

    public EmployerDetails(String companyName, String companyAddress) {
        if (companyName == null || companyName.trim().isEmpty() || companyName.trim().length() > 50) {
            throw new IllegalArgumentException("Company name must be 1-50 characters.");
        }
        if (companyAddress == null || companyAddress.trim().isEmpty() || companyAddress.trim().length() > 100) {
            throw new IllegalArgumentException("Company address must be 1-100 characters.");
        }

        this.companyName = companyName.trim();
        this.companyAddress = companyAddress.trim();
    }

    public boolean isEmployed() {
        return companyName != null && !companyName.isEmpty()
                && companyAddress != null && !companyAddress.isEmpty();
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }
}
