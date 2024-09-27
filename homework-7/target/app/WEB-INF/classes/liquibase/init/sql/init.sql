-- Create the Roles table - ManyToOne relationship (many users can have one role)
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Create the Address table - OneToOne relationship (one customer has one address)
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(100),
    city VARCHAR(50),
    postal_code VARCHAR(20)
);

-- Create the Users table (OneToOne with Customers, ManyToOne with Roles)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT,
    CONSTRAINT fk_role FOREIGN KEY(role_id) REFERENCES roles(id)
);

-- Create the Customers table - OneToOne relationship (one user has one customer profile)
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    address_id BIGINT,
    user_id BIGINT UNIQUE,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_address FOREIGN KEY(address_id) REFERENCES address(id)
);

-- Create the Accounts table - OneToMany relationship (one customer can have many accounts)
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(20) NOT NULL,
    balance NUMERIC(15, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    iban VARCHAR(28) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer_id BIGINT,
    CONSTRAINT fk_customer FOREIGN KEY(customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Create the Cards table - ManyToOne relationship (one account can have many cards, but each card belongs to one account)
CREATE TABLE cards (
    id BIGSERIAL PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL UNIQUE,
    card_type VARCHAR(20) NOT NULL,
    expiration_date DATE NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_id BIGINT,
    CONSTRAINT fk_account FOREIGN KEY(account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- Create the Transactions table - ManyToMany relationship (one transaction can involve multiple accounts)
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(15, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    currency VARCHAR(3) NOT NULL
);

-- Create the linking table for Transactions and Accounts (ManyToMany)
CREATE TABLE transaction_accounts (
    transaction_id BIGINT,
    account_id BIGINT,
    CONSTRAINT fk_transaction FOREIGN KEY(transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    CONSTRAINT fk_account_transaction FOREIGN KEY(account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    PRIMARY KEY (transaction_id, account_id)
);