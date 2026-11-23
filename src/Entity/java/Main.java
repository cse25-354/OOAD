package Entity.java;

import java.util.Scanner;

public class Main {
    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        int mainChoice = 0;

        while (mainChoice != 6) {
            System.out.println("\n=== BANKING SYSTEM ===");
            System.out.println("1. Individual & Company");
            System.out.println("2. Investment Account");
            System.out.println("3. Savings Account");
            System.out.println("4. Cheque Account");
            System.out.println("5. Employer Details");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            try {
                mainChoice = Integer.parseInt(keyboard.nextLine().trim());
                if (mainChoice == 1) handleCustomerMenu();
                else if (mainChoice == 2) handleInvestmentMenu();
                else if (mainChoice == 3) handleSavingsMenu();
                else if (mainChoice == 4) handleChequeMenu();
                else if (mainChoice == 5) handleEmployerMenu();
                else if (mainChoice != 6) System.out.println("Choose 1-6.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            }
        }

        System.out.println("Goodbye!");
        keyboard.close();
    }

    // 1. INDIVIDUAL & COMPANY //
    static void handleCustomerMenu() {
        Individual myIndividual = null;
        Company myCompany = null;
        int custChoice = 0;

        while (custChoice != 5) {
            System.out.println("\n1. Create Individual  2. Create Company  3. Update Contact  4. View Details  5. Exit");
            System.out.print("Choice: ");

            try {
                custChoice = Integer.parseInt(keyboard.nextLine().trim());

                if (custChoice == 1) {
                    if (myIndividual != null) throw new IllegalArgumentException("Individual already exists.");

                    String id = promptText("Customer ID", "Customer ID cannot be empty.");
                    if (id == null) continue;
                    String address = promptText("Physical Address", "Physical address cannot be empty.");
                    if (address == null) continue;
                    String email = promptEmail("Email Address");
                    if (email == null) continue;
                    String firstName = promptText("First Name", "First name cannot be empty.");
                    if (firstName == null) continue;
                    String surname = promptText("Surname", "Surname cannot be empty.");
                    if (surname == null) continue;
                    String omang = promptOmang();
                    if (omang == null) continue;

                    myIndividual = new Individual(id, address, email, firstName, surname, omang);
                    System.out.println("Individual customer created!");

                } else if (custChoice == 2) {
                    if (myCompany != null) throw new IllegalArgumentException("Company already exists.");

                    String id = promptText("Customer ID", "Customer ID cannot be empty.");
                    if (id == null) continue;
                    String address = promptText("Physical Address", "Physical address cannot be empty.");
                    if (address == null) continue;
                    String email = promptEmail("Email Address");
                    if (email == null) continue;
                    String compName = promptMaxText("Company Name", 50, "Company name must be 1-50 characters.");
                    if (compName == null) continue;
                    String regNum = promptText("Registration Number", "Registration number cannot be empty.");
                    if (regNum == null) continue;
                    String taxId = promptText("Tax ID", "Tax ID cannot be empty.");
                    if (taxId == null) continue;

                    myCompany = new Company(id, address, email, compName, regNum, taxId);
                    System.out.println("Company customer created!");

                } else if (custChoice == 3) {
                    if (myIndividual == null && myCompany == null)
                        throw new IllegalArgumentException("Create an Individual or Company first.");

                    String newAddress = promptText("New Physical Address", "Physical address cannot be empty.");
                    if (newAddress == null) continue;
                    String newEmail = promptEmail("New Email Address");
                    if (newEmail == null) continue;

                    if (myIndividual != null) myIndividual.updateContactInfo(newAddress, newEmail);
                    if (myCompany != null) myCompany.updateContactInfo(newAddress, newEmail);
                    System.out.println("Contact info updated!");

                } else if (custChoice == 4) {
                    if (myIndividual == null && myCompany == null)
                        throw new IllegalArgumentException("Create an Individual or Company first.");

                    if (myIndividual != null) {
                        System.out.println("Individual: " + myIndividual.getFirstName() + " " + myIndividual.getSurname()
                                + " | ID: " + myIndividual.getCustomerId()
                                + " | Omang: " + myIndividual.getOmangID()
                                + " | Address: " + myIndividual.getPhysicalAddress()
                                + " | Email: " + myIndividual.getEmailAddress());
                    }
                    if (myCompany != null) {
                        System.out.println("Company: " + myCompany.getCompanyName()
                                + " | ID: " + myCompany.getCustomerId()
                                + " | Reg: " + myCompany.getRegNumber()
                                + " | Tax ID: " + myCompany.getTaxId()
                                + " | Address: " + myCompany.getPhysicalAddress()
                                + " | Email: " + myCompany.getEmailAddress());
                    }
                } else if (custChoice != 5) {
                    throw new IllegalArgumentException("Choose 1-5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // 2. INVESTMENT ACCOUNT //
    static void handleInvestmentMenu() {
        InvestmentAccount myInvestment = null;
        int investChoice = 0;

        while (investChoice != 6) {
            System.out.println("\n1. Create Account  2. Deposit  3. Withdraw  4. Balance  5. Transactions  6. Exit");
            System.out.print("Choice: ");

            try {
                investChoice = Integer.parseInt(keyboard.nextLine().trim());

                if (investChoice == 1) {
                    if (myInvestment != null) throw new IllegalArgumentException("Account already exists.");

                    String accNum = promptAccountNumber();
                    if (accNum == null) continue;
                    double balance = promptAmount("Initial Deposit (Min BWP 500.00)", 500.00,
                            "Initial deposit must be at least BWP 500.00");
                    if (balance == -1) continue;

                    myInvestment = new InvestmentAccount(accNum, balance);
                    System.out.println("Investment account created!");

                } else if (investChoice >= 2 && investChoice <= 5) {
                    if (myInvestment == null) throw new IllegalArgumentException("Create an account first.");

                    if (investChoice == 2) {
                        handleDeposit(myInvestment);
                    } else if (investChoice == 3) {
                        handleWithdraw(myInvestment);
                    } else if (investChoice == 4) {
                        System.out.println("Balance: " + myInvestment.getbalance());
                    } else {
                        printTransactions(myInvestment);
                    }
                } else if (investChoice != 6) {
                    System.out.println("Choose 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // 3. SAVINGS ACCOUNT //
    static void handleSavingsMenu() {
        SavingsAccount mySavings = null;
        int savingsChoice = 0;

        while (savingsChoice != 6) {
            System.out.println("\n1. Create Account  2. Deposit  3. Withdraw  4. Balance  5. Transactions  6. Exit");
            System.out.print("Choice: ");

            try {
                savingsChoice = Integer.parseInt(keyboard.nextLine().trim());

                if (savingsChoice == 1) {
                    if (mySavings != null) throw new IllegalArgumentException("Account already exists.");

                    String accNum = promptAccountNumber();
                    if (accNum == null) continue;
                    double balance = promptAmount("Initial Deposit", 0.01, "Deposit must be greater than 0.");
                    if (balance == -1) continue;

                    mySavings = new SavingsAccount(accNum, balance);
                    System.out.println("Savings account created!");

                } else if (savingsChoice >= 2 && savingsChoice <= 5) {
                    if (mySavings == null) throw new IllegalArgumentException("Create an account first.");

                    if (savingsChoice == 2) {
                        handleDeposit(mySavings);
                    } else if (savingsChoice == 3) {

                        mySavings.withdraw(0); // Immediately blocks withdrawal
                    } else if (savingsChoice == 4) {
                        System.out.println("Balance: " + mySavings.getbalance());
                    } else {
                        printTransactions(mySavings);
                    }
                } else if (savingsChoice != 6) {
                    System.out.println("Choose 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    // 4. CHEQUE ACCOUNT //
    static void handleChequeMenu() {
        ChequeAccount myCheque = null;
        int choice = 0;
        while (choice != 6) {
            System.out.println("\n1. Create Account  2. Deposit  3. Withdraw  4. Balance  5. Transactions  6. Exit");
            System.out.print("Choice: ");
            try {
                choice = Integer.parseInt(keyboard.nextLine().trim());
                if (choice == 1) {
                    if (myCheque != null) throw new IllegalArgumentException("Account already exists.");
                    String accNum = promptAccountNumber();
                    if (accNum == null) continue;
                    double balance = promptAmount("Initial Deposit", 0.01, "Deposit must be greater than 0.");
                    if (balance == -1) continue;
                    String name = promptMaxText("Company Name", 50, "Company name must be 1-50 characters.");
                    if (name == null) continue;
                    String address = promptMaxText("Company Address", 100, "Company address must be 1-100 characters.");
                    if (address == null) continue;
                    myCheque = new ChequeAccount(accNum, balance, name, address);
                    System.out.println("Account created!");
                } else if (choice >= 2 && choice <= 5) {
                    if (myCheque == null) throw new IllegalArgumentException("Create an account first.");
                    if (choice == 2) {
                        handleDeposit(myCheque);
                    } else if (choice == 3) {
                        handleWithdraw(myCheque);
                    } else if (choice == 4) {
                        System.out.println("Balance: " + myCheque.getbalance());
                    } else {
                        printTransactions(myCheque);
                    }
                } else if (choice != 6) {
                    System.out.println("Choose 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    // 5. EMPLOYER DETAILS //
    static void handleEmployerMenu() {
        EmployerDetails myEmployer = null;
        int empChoice = 0;
        while (empChoice != 3) {
            System.out.println("\n1. Add Employer Details  2. View Employer Details  3. Exit");
            System.out.print("Choice: ");
            try {
                empChoice = Integer.parseInt(keyboard.nextLine().trim());
                if (empChoice == 1) {
                    if (myEmployer != null) throw new IllegalArgumentException("Employer details already exist.");
                    String compName = promptMaxText("Company Name", 50, "Company name must be 1-50 characters.");
                    if (compName == null) continue;
                    String compAddress = promptMaxText("Company Address", 100, "Company address must be 1-100 characters.");
                    if (compAddress == null) continue;
                    myEmployer = new EmployerDetails(compName, compAddress);
                    System.out.println("Employer details saved!");
                } else if (empChoice == 2) {
                    if (myEmployer == null) throw new IllegalArgumentException("No employer details added yet.");
                    System.out.println("Company: " + myEmployer.getCompanyName()
                            + " | Address: " + myEmployer.getCompanyAddress()
                            + " | Employed: " + myEmployer.isEmployed());
                } else if (empChoice != 3) {
                    throw new IllegalArgumentException("Choose 1-3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    // REUSABLE STAGE HELPERS //
    static String promptText(String label, String errorMsg) {
        while (true) {
            try {
                System.out.println(label);
                System.out.println("2.go back");
                System.out.print("Enter: ");
                String val = keyboard.nextLine().trim();
                if (val.equals("2")) return null;
                if (val.isEmpty()) throw new IllegalArgumentException(errorMsg);
                return val;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static String promptMaxText(String label, int maxLen, String errorMsg) {
        while (true) {
            try {
                System.out.println(label);
                System.out.println("2.go back");
                System.out.print("Enter: ");
                String val = keyboard.nextLine().trim();
                if (val.equals("2")) return null;
                if (val.isEmpty() || val.length() > maxLen) throw new IllegalArgumentException(errorMsg);
                return val;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static String promptEmail(String label) {
        while (true) {
            try {
                System.out.println(label);
                System.out.println("2.go back");
                System.out.print("Enter: ");
                String val = keyboard.nextLine().trim();
                if (val.equals("2")) return null;
                if (!val.contains("@")) throw new IllegalArgumentException("Invalid email address.");
                return val;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static String promptOmang() {
        while (true) {
            try {
                System.out.println("Omang ID (9 digits)");
                System.out.println("2.go back");
                System.out.print("Enter: ");
                String val = keyboard.nextLine().trim();
                if (val.equals("2")) return null;
                if (!val.matches("[0-9]{9}")) throw new IllegalArgumentException("Omang ID must be 9 digits.");
                return val;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static String promptAccountNumber() {
        while (true) {
            try {
                System.out.println("Account Number (10 chars)");
                System.out.println("2.go back");
                System.out.print("Enter: ");
                String val = keyboard.nextLine().trim();
                if (val.equals("2")) return null;
                if (val.length() != 10) throw new IllegalArgumentException("Account number must be 10 characters.");
                return val;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static double promptAmount(String label, double minAmount, String errorMsg) {
        while (true) {
            System.out.println(label);
            System.out.println("2.go back");
            System.out.print("Enter: ");
            String input = keyboard.nextLine().trim();
            if (input.equals("2")) return -1;
            try {
                double val = Double.parseDouble(input);
                if (val < minAmount) throw new IllegalArgumentException(errorMsg);
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static void handleDeposit(Account acc) {
        while (true) {
            System.out.println("Deposit amount");
            System.out.println("2.go back");
            System.out.print("Enter: ");
            String input = keyboard.nextLine().trim();
            if (input.equals("2")) break;
            try {
                acc.deposit(Double.parseDouble(input));
                System.out.println("New Balance: " + acc.getbalance());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static void handleWithdraw(Account acc) {
        while (true) {
            System.out.println("Withdraw amount");
            System.out.println("2.go back");
            System.out.print("Enter: ");
            String input = keyboard.nextLine().trim();
            if (input.equals("2")) break;
            try {
                acc.withdraw(Double.parseDouble(input));
                System.out.println("New Balance: " + acc.getbalance());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    static void printTransactions(Account acc) {
        for (Transaction t : acc.getTransactions()) {
            System.out.println(t);
        }
    }
}