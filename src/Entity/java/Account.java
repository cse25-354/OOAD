package Entity.java;

import java.util.List;

public interface Account {
    void withdraw(double amount);
    void deposit(double amount);
    double getbalance();
    void MonthlyInterest();
    String getAccountNumber();
    List<Transaction> getTransactions();
}
