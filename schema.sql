CREATE TABLE IF NOT EXISTS customers (
                                         customer_id TEXT PRIMARY KEY,
                                         name TEXT NOT NULL,
                                         password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
                                        account_number TEXT PRIMARY KEY,
                                        customer_id TEXT NOT NULL,
                                        account_type TEXT NOT NULL,
                                        balance REAL NOT NULL,
                                        FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
    );

CREATE TABLE IF NOT EXISTS transactions (
                                            transaction_id TEXT PRIMARY KEY,
                                            account_number TEXT NOT NULL,
                                            type TEXT NOT NULL,
                                            amount REAL NOT NULL,
                                            date TEXT NOT NULL,
                                            FOREIGN KEY (account_number) REFERENCES accounts(account_number)
    );
