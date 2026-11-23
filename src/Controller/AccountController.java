package Controller;

import Boundary.AccountView;
import DAO.AccountDAO;
import DAO.TransactionDAO;
import Entity.java.Account;
import Entity.java.ChequeAccount;
import Entity.java.EmployerDetails;
import Entity.java.InvestmentAccount;
import Entity.java.SavingsAccount;
import Entity.java.Transaction;
import java.time.LocalDate;

public class AccountController {
    private AccountView view;
    private CustomerController customerController;
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    private InvestmentAccount investmentAccount;
    private SavingsAccount savingsAccount;
    private ChequeAccount chequeAccount;

    public AccountController(AccountView view, CustomerController customerController) {
        this.view = view;
        this.customerController = customerController;

        this.view.getCreateAccountButton().setOnAction(e -> createAccount());
        this.view.getSearchButton().setOnAction(e -> searchAccount());
        this.view.getDepositButton().setOnAction(e -> handleDeposit());
        this.view.getWithdrawButton().setOnAction(e -> handleWithdraw());
    }

    private void createAccount() {
        try {
            String type = view.getSelectedAccountType();
            String accNum = view.getAccountNumber().trim();
            double amount = Double.parseDouble(view.getAmount().trim());
            String custId = customerController.getCurrentCustomerId();

            if (accNum.length() != 10) {
                view.setMessage("Error: Account number must be 10 characters.");
                return;
            }

            if (type.equals("Investment Account")) {
                if (amount < 500.00) {
                    view.setMessage("Error: Minimum deposit for Investment is BWP 500.00.");
                    return;
                }
                investmentAccount = new InvestmentAccount(accNum, amount);
                accountDAO.addAccount(accNum, custId, type, amount);
                saveTransaction(accNum, "Initial Deposit", amount);
                updateDisplay(investmentAccount, "Investment Account created!");
            } else if (type.equals("Savings Account")) {
                if (amount <= 0) {
                    view.setMessage("Error: Deposit must be greater than 0.");
                    return;
                }
                savingsAccount = new SavingsAccount(accNum, amount);
                accountDAO.addAccount(accNum, custId, type, amount);
                saveTransaction(accNum, "Initial Deposit", amount);
                updateDisplay(savingsAccount, "Savings Account created!");
            } else {
                EmployerDetails emp = customerController.getEmployer();
                if (emp == null) {
                    view.setMessage("Error: Add Employer Details on the Customer screen first.");
                    return;
                }
                if (amount <= 0) {
                    view.setMessage("Error: Deposit must be greater than 0.");
                    return;
                }
                chequeAccount = new ChequeAccount(accNum, amount, emp.getCompanyName(), emp.getCompanyAddress());
                accountDAO.addAccount(accNum, custId, type, amount);
                saveTransaction(accNum, "Initial Deposit", amount);
                updateDisplay(chequeAccount, "Cheque Account created!");
            }
        } catch (NumberFormatException ex) {
            view.setMessage("Error: Enter a valid number in Amount.");
        } catch (IllegalArgumentException ex) {
            view.setMessage("Error: " + ex.getMessage());
        }
    }

    private void searchAccount() {
        Account acc = getSelectedAccount();
        if (acc == null) {
            String accNum = view.getAccountNumber().trim();
            double dbBalance = accountDAO.getBalance(accNum);
            if (dbBalance > 0) {
                view.setBalance(String.format("%.2f", dbBalance));
                view.setMessage("Found account in database!");
            } else {
                view.setMessage("No " + view.getSelectedAccountType() + " created yet.");
                view.setBalance("");
                view.setTransactions("");
            }
            return;
        }
        updateDisplay(acc, "Loaded " + view.getSelectedAccountType());
    }

    private void handleDeposit() {
        try {
            Account acc = getSelectedAccount();
            if (acc == null) {
                view.setMessage("Error: Create or search for an account first.");
                return;
            }
            double amount = Double.parseDouble(view.getAmount().trim());
            acc.deposit(amount);

            accountDAO.updateBalance(acc.getAccountNumber(), acc.getbalance());
            saveTransaction(acc.getAccountNumber(), "Deposit", amount);

            updateDisplay(acc, "Deposit successful!");
        } catch (NumberFormatException ex) {
            view.setMessage("Error: Enter a valid deposit amount.");
        } catch (IllegalArgumentException ex) {
            view.setMessage("Error: " + ex.getMessage());
        }
    }

    private void handleWithdraw() {
        try {
            Account acc = getSelectedAccount();
            if (acc == null) {
                view.setMessage("Error: Create or search for an account first.");
                return;
            }
            double amount = Double.parseDouble(view.getAmount().trim());
            acc.withdraw(amount);

            accountDAO.updateBalance(acc.getAccountNumber(), acc.getbalance());
            saveTransaction(acc.getAccountNumber(), "Withdrawal", amount);

            updateDisplay(acc, "Withdrawal successful!");
        } catch (NumberFormatException ex) {
            view.setMessage("Error: Enter a valid withdrawal amount.");
        } catch (IllegalArgumentException | UnsupportedOperationException ex) {
            view.setMessage("Error: " + ex.getMessage());
        }
    }

    private void saveTransaction(String accNum, String type, double amount) {
        String transId = "TXN-" + System.currentTimeMillis();
        String today = LocalDate.now().toString();
        transactionDAO.addTransaction(transId, accNum, type, amount, today);
    }

    private Account getSelectedAccount() {
        String type = view.getSelectedAccountType();
        if (type.equals("Investment Account")) return investmentAccount;
        if (type.equals("Savings Account")) return savingsAccount;
        return chequeAccount;
    }

    private void updateDisplay(Account acc, String message) {
        view.setBalance(String.format("%.2f", acc.getbalance()));
        view.setMessage(message);

        StringBuilder history = new StringBuilder();
        for (Transaction t : acc.getTransactions()) {
            history.append(t.toString()).append("\n");
        }
        view.setTransactions(history.toString());
    }
}
