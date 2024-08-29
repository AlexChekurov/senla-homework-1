CREATE TABLE banking.customers
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(50)       NOT NULL,
    last_name   VARCHAR(50)       NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    phone       VARCHAR(15) UNIQUE  NOT NULL,
    street      VARCHAR(100)      NOT NULL,
    city        VARCHAR(50)       NOT NULL,
    state       VARCHAR(50)       NOT NULL,
    postal_code VARCHAR(20)       NOT NULL,
    country     VARCHAR(50)       NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id     INT               NOT NULL,
    FOREIGN KEY (user_id) REFERENCES banking.users (id)
);