DROP DATABASE IF EXISTS numeric_bank;
CREATE DATABASE numeric_bank;

\c numeric_bank;

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    first_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('ENTRY', 'EXIT'))
);

CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    balance REAL NOT NULL,
    net_Monthly_salary REAL DEFAULT NULL,
    account_number VARCHAR(255) UNIQUE,
    overdraft_limit REAL NOT NULL,
    last_transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    overdraft_enabled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(id),
    amount REAL NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(50) NOT NULL,
    category_id INTEGER REFERENCES transaction_categories(id)
);


CREATE TABLE IF NOT EXISTS transfers (
    id BIGSERIAL PRIMARY KEY,
    sender_account_id INTEGER REFERENCES accounts(id),
    receiver_account_id INTEGER REFERENCES accounts(id),
    amount REAL NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'Pending',
    transfer_type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions_history (
    id BIGSERIAL PRIMARY KEY,
    account_id INTEGER,
    amount REAL NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    category_id INTEGER,
    operation_type VARCHAR(50) NOT NULL,
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interests (
    id BIGSERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(id),
    amount REAL NOT NULL,
    interest_rate real NOT NULL,
    interest_date DATE NOT NULL
);
