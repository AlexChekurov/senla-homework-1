CREATE TABLE banking.users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    role_id    INT                NOT NULL,
    FOREIGN KEY (role_id) REFERENCES banking.roles (id)
);