package Entity.java;

public class Individual extends Customer {
    private String firstName;
    private String surname;
    private String omangID;

    public Individual(String customerId, String physicalAddress, String emailAddress,
                      String firstName, String surname, String omangID) {
        super(customerId, physicalAddress, emailAddress);

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }
        if (surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be empty.");
        }
        if (omangID == null || !omangID.trim().matches("\\d{9}")) {
            throw new IllegalArgumentException("Omang ID must be 9 digits.");
        }

        this.firstName = firstName.trim();
        this.surname = surname.trim();
        this.omangID = omangID.trim();
    }

    public boolean verifyIdentity(String inputOmangID) {
        if (inputOmangID == null) {
            return false;
        }
        return this.omangID.equals(inputOmangID.trim());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getOmangID() {
        return omangID;
    }
}
