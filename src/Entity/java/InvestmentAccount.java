package Entity.java;

import java.util.ArrayList;
import java.util.List;

public class InvestmentAccount implements Account {
    private static final double INTEREST_RATE = 0.05; // Fixed 5% rate set by the bank
    private String accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public InvestmentAccount(String accountNumber, double balance) {
        if (accountNumber == null || accountNumber.length() != 10) {
            throw new IllegalArgumentException("Account number must be 10 characters.");
        }
        if (balance < 500.00) {
            throw new IllegalArgumentException("Initial deposit must be at least BWP 500.00");
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
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be greater than BWP 0.");
        }
        this.balance += amount;
        this.transactions.add(new Transaction("TX-" + (transactions.size() + 1), "DEPOSIT", amount));
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactions.add(new Transaction("TX-" + (transactions.size() + 1), "WITHDRAWAL", amount));
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or insufficient funds.");
        }
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
