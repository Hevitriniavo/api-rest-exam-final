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
    user_id BIGINT NOT NULL REFERENCES users(id),
    balance REAL NOT NULL,
    net_Monthly_salary REAL DEFAULT NULL,
    account_number VARCHAR(14) UNIQUE,
    overdraft_limit REAL,
    overdraft_enabled BOOLEAN DEFAULT FALSE,
    creation_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount REAL NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(50) NOT NULL,
    category_id BIGINT REFERENCES transaction_categories(id)
);


CREATE TABLE IF NOT EXISTS transfers (
    id BIGSERIAL PRIMARY KEY,
    sender_account_id BIGINT REFERENCES accounts(id),
    receiver_account_id BIGINT REFERENCES accounts(id),
    amount REAL NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'Pending',
    transfer_type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions_history (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount REAL NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    record_date TIMESTAMP NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    category_id BIGINT REFERENCES transaction_categories(id),
    operation_type VARCHAR(50) NOT NULL,
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interests (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount REAL NOT NULL,
    interest_rate real NOT NULL,
    interest_date DATE NOT NULL
);

CREATE SEQUENCE account_id_seq;

CREATE OR REPLACE FUNCTION generate_account_number() RETURNS TRIGGER AS $$
BEGIN
    NEW.account_number := 'ABC' || LPAD(CAST(nextval('account_id_seq') AS TEXT), 11, '0');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER account_number_trigger
    BEFORE INSERT ON accounts
    FOR EACH ROW
    EXECUTE FUNCTION generate_account_number();

