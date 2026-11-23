package Entity.java;

import java.util.ArrayList;
import java.util.List;

public class SavingsAccount implements Account {
    private static final double INTEREST_RATE = 0.05; // Fixed 5% rate set by the bank
    private String accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public SavingsAccount(String accountNumber, double balance) {
        if (accountNumber == null || accountNumber.length() != 10) {
            throw new IllegalArgumentException("Account number must be 10 characters.");
        }
        if (balance <= 0) {
            throw new IllegalArgumentException("Deposit must be greater than 0.");
        }

        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.transactions.add(new Transaction("TX-1", "DEPOSIT", balance));
    }

    @Override
    public String getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public double getbalance() {
        return this.balance;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            this.transactions.add(new Transaction("TX-" + (transactions.size() + 1), "DEPOSIT", amount));
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not permitted from a savings account.");
    }

    @Override
    public void MonthlyInterest() {
        double interestEarned = this.balance * INTEREST_RATE;
        this.balance += interestEarned;
        System.out.println("Interest applied: +" + interestEarned + ". New Balance: " + this.balance);
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public double getInterestRate() {
        return INTEREST_RATE;
    }
}
