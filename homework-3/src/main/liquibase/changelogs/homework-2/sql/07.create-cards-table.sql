CREATE TABLE banking.cards
(
    id              SERIAL PRIMARY KEY,
    account_id      INT                NOT NULL,
    customer_id     INT                NOT NULL,
    card_number     VARCHAR(20) UNIQUE NOT NULL,
    card_type       VARCHAR(20)        NOT NULL,
    expiration_date DATE               NOT NULL,
    cvv             VARCHAR(4)         NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES banking.accounts (id),
    FOREIGN KEY (customer_id) REFERENCES banking.customers (id)
);
