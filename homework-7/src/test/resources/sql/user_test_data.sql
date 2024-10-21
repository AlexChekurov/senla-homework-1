DELETE FROM roles;
DELETE FROM users;

INSERT INTO roles (id, name)
VALUES (76543, 'USER_CUSTOMER');
INSERT INTO users (id, username, password, role_id)
VALUES (1425487, 'alex_doe', 'hashed_password_here', 76543);

INSERT INTO roles (id, name)
VALUES (23451322345, 'USER_CUSTOMER_1');
INSERT INTO users (id, username, password, role_id)
VALUES (7893, 'john_doe1221', 'hashed_password_here', 23451322345);

INSERT INTO roles (id, name)
VALUES (43678, 'ROLE_FOR_NEW_USER');

