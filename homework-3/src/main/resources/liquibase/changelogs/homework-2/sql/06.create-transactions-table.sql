CREATE TABLE banking.transactions
(
    id                     BIGSERIAL PRIMARY KEY,
    amount                 NUMERIC(15, 2) NOT NULL,
    transaction_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    source_account_id      BIGINT NOT NULL,
    destination_account_id BIGINT NOT NULL,
    currency               VARCHAR(3)   NOT NULL,
    FOREIGN KEY (source_account_id) REFERENCES banking.accounts (id),
    FOREIGN KEY (destination_account_id) REFERENCES banking.accounts (id)
);
