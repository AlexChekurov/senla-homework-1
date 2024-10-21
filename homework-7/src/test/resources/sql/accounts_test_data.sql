DELETE FROM accounts;
DELETE FROM customers;
DELETE FROM users;
DELETE FROM address;
DELETE FROM roles;

INSERT INTO roles (id, name)
VALUES (32, 'USER');

INSERT INTO address (id, street, city, postal_code)
VALUES (123, '123 Main St', 'New York', '10001');

INSERT INTO users (id, username, password, role_id)
VALUES (11, 'john_doe', 'hashed_password_here', 32);

INSERT INTO customers (id, first_name, last_name, email, phone, address_id, user_id)
VALUES (15, 'John', 'Doe', 'john.doe@example.com', '+1234567890',123 , 11);

INSERT INTO accounts (id, account_number, account_type, balance, currency, iban, customer_id)
VALUES (14, '322322344', 'SAVINGS', 1000.00, 'USD', 'US12345678901234567890123456', 15);

INSERT INTO accounts (id, account_number, account_type, balance, currency, iban, customer_id)
VALUES (17, '322322345', 'SAVINGS', 1000.00, 'USD', 'AZ12345678901234567890123456', 15);
