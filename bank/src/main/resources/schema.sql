DROP DATABASE IF EXISTS numeric_bank;

CREATE DATABASE numeric_bank;

\c numeric_bank;

CREATE TABLE IF NOT EXISTS banks (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('ENTREE', 'SORTIE'))
);


CREATE TABLE IF NOT EXISTS bank_solds (
    id BIGSERIAL PRIMARY KEY,
    value DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS history_bank_solds (
    id BIGSERIAL PRIMARY KEY,
    value DECIMAL(10, 2) NOT NULL,
    bank_sold_id BIGINT REFERENCES bank_solds(id) ON DELETE CASCADE ON UPDATE CASCADE,
    bank_sold_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    birthday DATE NOT NULL  CHECK (birthday <= current_date - interval '21 years'),
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    net_monthly_salary REAL DEFAULT NULL,
    account_number VARCHAR(14) UNIQUE,
    overdraft_limit DECIMAL(10, 2),
    overdraft_enabled BOOLEAN DEFAULT FALSE,
    creation_date DATE DEFAULT CURRENT_DATE,
    last_withdrawal_date DATE,
    bank_sold_id BIGINT NULL REFERENCES bank_solds(id) ON DELETE CASCADE ON UPDATE CASCADE
);

SELECT t.*, a.* FROM transactions t INNER JOIN accounts a ON t.account_id = a.id WHERE t.account_id = 2 LIMIT 1;

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount DECIMAL(10, 2) NOT NULL,
    reason VARCHAR(255),
    transaction_type VARCHAR(50) NOT NULL,
    category_id BIGINT REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE,
    comment VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transaction_histories (
    id BIGSERIAL PRIMARY KEY,
    transaction_id BIGINT REFERENCES transactions(id) ON DELETE CASCADE ON UPDATE CASCADE,
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transfers (
    id BIGSERIAL PRIMARY KEY,
    sender_account_id BIGINT REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    receiver_account_id BIGINT REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    bank_id BIGINT NOT NULL REFERENCES banks(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    reason VARCHAR(255),
    effective_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(255) DEFAULT 'PENDING'  CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED')),
    reference VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS interest (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    interest_rate DECIMAL(10, 2) NOT NULL,
    interest_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS history_interests (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    interest_rate DECIMAL(10, 2) NOT NULL,
    interest_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS loan_valid (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    loans_date DATE NOT NULL,
    status INT NOT NULL CHECK (status IN (0, 100))
);


CREATE TABLE IF NOT EXISTS History_loans (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    loans_date DATE NOT NULL,
    status INT NOT NULL CHECK (status IN (0, 100))
);

CREATE TABLE IF NOT EXISTS loan_not_valid (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    loans_date DATE NOT NULL
);


CREATE SEQUENCE account_id_seq;

CREATE SEQUENCE transfer_id_seq;

CREATE OR REPLACE FUNCTION generate_account_number() RETURNS TRIGGER AS $$
BEGIN
    NEW.account_number := 'MOI' || LPAD(CAST(nextval('account_id_seq') AS TEXT), 11, '0');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER account_number_trigger
    BEFORE INSERT ON accounts
    FOR EACH ROW
    EXECUTE FUNCTION generate_account_number();


CREATE OR REPLACE FUNCTION generate_transfer_reference() RETURNS TRIGGER AS $$
BEGIN
    NEW.reference := 'TRX' || to_char(current_date, 'YYYYMMDD') || lpad(nextval('transfer_id_seq')::TEXT, 18, '0');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER transfer_reference_trigger
    BEFORE INSERT ON transfers
    FOR EACH ROW
    EXECUTE FUNCTION generate_transfer_reference();

