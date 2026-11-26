CREATE TABLE IF NOT EXISTS customers (
                                         customer_id TEXT PRIMARY KEY,
                                         customer_type TEXT NOT NULL,
                                         address TEXT NOT NULL,
                                         email TEXT NOT NULL,
                                         first_name TEXT,
                                         surname TEXT,
                                         omang_id TEXT,
                                         company_name TEXT,
                                         registration_number TEXT,
                                         tax_id TEXT,
                                         employer_address TEXT
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

CREATE TABLE IF NOT EXISTS employees (
                                         username TEXT PRIMARY KEY,
                                         password TEXT NOT NULL
);
