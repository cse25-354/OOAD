package Entity.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionId;
    private String type; // "DEPOSIT" or "WITHDRAWAL"
    private double amount;
    private String timestamp;

    public Transaction(String transactionId, String type, double amount) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID cannot be empty.");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction type cannot be empty.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        this.transactionId = transactionId.trim();
        this.type = type.trim().toUpperCase();
        this.amount = amount;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + transactionId + " - " + type + ": BWP " + amount;
    }
}
