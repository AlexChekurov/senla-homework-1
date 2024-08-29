CREATE TABLE banking.accounts
(
    id             SERIAL PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type   VARCHAR(20)        NOT NULL,
    balance        NUMERIC(15, 2)     NOT NULL,
    currency       VARCHAR(3)         NOT NULL,
    iban           CHAR(28)           UNIQUE NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
