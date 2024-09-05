CREATE TABLE banking.customers_accounts
(
    id          SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    account_id  INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES banking.customers (id),
    FOREIGN KEY (account_id) REFERENCES banking.accounts (id)
);
