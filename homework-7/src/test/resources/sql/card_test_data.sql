DELETE FROM transaction_accounts WHERE account_id IN (115555, 116666);
DELETE FROM cards WHERE id IN (117777, 118888);
DELETE FROM transactions WHERE from_account_id IN (115555) OR to_account_id IN (116666);
DELETE FROM accounts WHERE id IN (115555, 116666);
DELETE FROM customers WHERE id IN (114444);
DELETE FROM users WHERE id IN (113333);
DELETE FROM address WHERE id IN (112222);
DELETE FROM roles WHERE id IN (24524);

INSERT INTO roles (id, name) VALUES (24524, 'USER_24524');
INSERT INTO address (id, street, city, postal_code) VALUES (112222, '123 Main St', 'Anytown', '12345');
INSERT INTO users (id, username, password, role_id) VALUES (113333, 'john_doe_113333', 'password_113333', 24524);
INSERT INTO customers (id, first_name, last_name, email, phone, address_id, user_id)
VALUES (114444, 'John', 'Doe', 'john.doe113333@example.com', '1234567890', 112222, 113333);
INSERT INTO accounts (id, account_number, account_type, balance, currency, iban, customer_id)
VALUES (115555, '123454123', 'SAVINGS', 1000.00, 'USD', 'AX12345678901234567890123456', 114444),
       (116666, '0947854235', 'CHECKING', 500.00, 'USD', 'KZ09876543210987654321098765', 114444);

DELETE FROM cards WHERE id IN (117777, 118888);

INSERT INTO cards (id, card_number, card_type, expiration_date, cvv, account_id)
VALUES (117777, '4445-5678-9101-1121', 'VISA', '2026-12-31', '123', 115555),
       (118888, '1234-5678-9101-1122', 'MASTERCARD', '2025-12-31', '456', 116666);
