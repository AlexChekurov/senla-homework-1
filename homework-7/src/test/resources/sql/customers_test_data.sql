DELETE FROM customers;
DELETE FROM users;

INSERT INTO roles (id, name)
VALUES (3234422, 'USER_CUSTOMER');

INSERT INTO roles (id, name)
VALUES (98765, 'USER_CUSTOMER_1');

INSERT INTO roles (id, name)
VALUES (12351245, 'USER_CUSTOMER_2');

INSERT INTO address (id, street, city, postal_code)
VALUES (9898, 'Old street', 'Minsk', '12341234');

INSERT INTO address (id, street, city, postal_code)
VALUES (5555555555, '123 Main St', 'New York', '10001');

INSERT INTO address (id, street, city, postal_code)
VALUES (23456, '123 Main St', 'New York', '10001');

INSERT INTO users (id, username, password, role_id)
VALUES (811, 'john_doe', 'hashed_password_here', 3234422);

INSERT INTO users (id, username, password, role_id)
VALUES (234653673673734, 'john_doe1221', 'hashed_password_here', 12351245);

INSERT INTO users (id, username, password, role_id)
VALUES (3211342, 'john_doe1', 'hashed_password_here', 98765);

INSERT INTO customers (id, first_name, last_name, email, phone, address_id, user_id)
VALUES (1511, 'John', 'Doe', 'john.doe@example.com', '+1234567890',5555555555 , 811);

INSERT INTO customers (id, first_name, last_name, email, phone, address_id, user_id)
VALUES (1415, 'John', 'Doe', 'john111.doe@example.com', '+331234567890',23456 , 3211342);