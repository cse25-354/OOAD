package Entity.java;

import java.util.ArrayList;
import java.util.List;

public class ChequeAccount implements Account {
    private String accountNumber;
    private double balance;
    private String companyName;
    private String companyAddress;
    private List<Transaction> transactions;

    public ChequeAccount(String accountNumber, double balance, String companyName, String companyAddress) {
        if (accountNumber == null || accountNumber.length() != 10) {
            throw new IllegalArgumentException("Account number must be 10 characters.");
        }
        if (balance <= 0) {
            throw new IllegalArgumentException("Deposit must be greater than 0.");
        }
        if (companyName == null || companyName.isEmpty() || companyName.length() > 50) {
            throw new IllegalArgumentException("Company name must be 1-50 characters.");
        }
        if (companyAddress == null || companyAddress.isEmpty() || companyAddress.length() > 100) {
            throw new IllegalArgumentException("Company address must be 1-100 characters.");
        }

        this.accountNumber = accountNumber;
        this.balance = balance;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction("TX-1", "DEPOSIT", balance));
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            this.transactions.add(new Transaction("TX-" + (transactions.size() + 1), "DEPOSIT", amount));
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            this.transactions.add(new Transaction("TX-" + (transactions.size() + 1), "WITHDRAWAL", amount));
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or insufficient funds.");
        }
    }

    @Override
    public void MonthlyInterest() {
        // Cheque accounts typically do not earn monthly interest
    }

    @Override
    public double getbalance() {
        return balance;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }
}
