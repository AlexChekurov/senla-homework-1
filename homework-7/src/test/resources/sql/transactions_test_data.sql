DELETE FROM transaction_accounts;
DELETE FROM transactions;
DELETE FROM accounts;
DELETE FROM customers;
DELETE FROM users;
DELETE FROM address;
DELETE FROM roles;

INSERT INTO roles (id, name) VALUES (101, 'USER');

INSERT INTO address (id, street, city, postal_code)
VALUES (202, '123 Main St', 'New York', '10001');

INSERT INTO users (id, username, password, role_id)
VALUES (303, 'john_doe', 'hashed_password_here', 101);

INSERT INTO customers (id, first_name, last_name, email, phone, address_id, user_id)
VALUES (404, 'John', 'Doe', 'john.doe@example.com', '+1234567890', 202, 303);

INSERT INTO accounts (id, account_number, account_type, balance, currency, iban, customer_id)
VALUES (505, '1234567890', 'SAVINGS', 1000.00, 'USD', 'US12345678901234567890123456', 404),
       (606, '0987654321', 'CHECKING', 500.00, 'USD', 'US09876543210987654321098765', 404);

INSERT INTO transactions (id, amount, transaction_date, currency, from_account_id, to_account_id)
VALUES
    (707, 100.00, CURRENT_TIMESTAMP, 'USD', 505, 606),
    (808, 200.50, CURRENT_TIMESTAMP, 'USD', 606, 505),
    (909, 300.75, CURRENT_TIMESTAMP, 'EUR', 505, 606);

INSERT INTO transaction_accounts (transaction_id, account_id)
VALUES
    (707, 505),
    (707, 606),
    (808, 606),
    (808, 505),
    (909, 505),
    (909, 606);
