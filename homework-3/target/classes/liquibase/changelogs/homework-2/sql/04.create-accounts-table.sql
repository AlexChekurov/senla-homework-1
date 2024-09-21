CREATE TABLE banking.accounts
(
    id             BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type   VARCHAR(20)        NOT NULL,
    balance        NUMERIC(15, 2)     NOT NULL,
    currency       VARCHAR(3)         NOT NULL,
    iban           VARCHAR(28)        UNIQUE NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
