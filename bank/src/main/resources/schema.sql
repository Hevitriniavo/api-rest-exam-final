DROP DATABASE IF EXISTS numeric_banks;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    first_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    birthday TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transaction_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('Entry', 'Exit')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    balance NUMERIC(10, 2) NOT NULL,
    net_Monthly_salary NUMERIC(10, 2) DEFAULT NULL,
    account_number VARCHAR(255) UNIQUE,
    overdraft_limit NUMERIC(10, 2) NOT NULL,
    overdraft_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(id),
    amount NUMERIC(10, 2) NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(50) NOT NULL,
    category_id INTEGER REFERENCES transaction_categories(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);


CREATE TABLE IF NOT EXISTS transfers (
    id SERIAL PRIMARY KEY,
    sender_account_id INTEGER REFERENCES accounts(id),
    receiver_account_id INTEGER REFERENCES accounts(id),
    amount NUMERIC(10, 2) NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'Pending',
    transfer_type VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50) UNIQUE NOT NULL DEFAULT uuid_generate_v4(),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions_history (
    id SERIAL PRIMARY KEY,
    account_id INTEGER,
    amount NUMERIC(10, 2) NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    category_id INTEGER,
    operation_type VARCHAR(50) NOT NULL,
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interests (
    id SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(id),
    amount NUMERIC(10, 2) NOT NULL,
    interest_rate NUMERIC(5, 2) NOT NULL,
    interest_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
