CREATE TABLE banking.customers_accounts
(
    id          BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    account_id  BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES banking.customers (id),
    FOREIGN KEY (account_id) REFERENCES banking.accounts (id)
);
