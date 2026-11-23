package Entity.java;

public class Customer {
    private String customerId;
    private String physicalAddress;
    private String emailAddress;

    public Customer(String customerId, String physicalAddress, String emailAddress) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty.");
        }
        if (physicalAddress == null || physicalAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Physical address cannot be empty.");
        }
        if (emailAddress == null || !emailAddress.trim().contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        this.customerId = customerId.trim();
        this.physicalAddress = physicalAddress.trim();
        this.emailAddress = emailAddress.trim();
    }

    public void updateContactInfo(String physicalAddress, String emailAddress) {
        if (physicalAddress == null || physicalAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Physical address cannot be empty.");
        }
        if (emailAddress == null || !emailAddress.trim().contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        this.physicalAddress = physicalAddress.trim();
        this.emailAddress = emailAddress.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
