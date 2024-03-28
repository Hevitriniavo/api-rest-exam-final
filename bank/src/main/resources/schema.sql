DROP DATABASE IF EXISTS numeric_bank;

CREATE DATABASE numeric_bank;

\c numeric_bank;

CREATE TABLE IF NOT EXISTS banks (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS bank_solds (
    id BIGSERIAL PRIMARY KEY,
    value DECIMAL(10, 2) DEFAULT 0.0 NOT NULL
);

CREATE TABLE IF NOT EXISTS bank_sold_histories (
    id BIGSERIAL PRIMARY KEY,
    value DECIMAL(10, 2) NOT NULL,
    bank_sold_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    birthday DATE NOT NULL,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    net_monthly_salary REAL DEFAULT NULL,
    account_number VARCHAR(14) UNIQUE,
    overdraft_limit DECIMAL(10, 2),
    overdraft_enabled BOOLEAN DEFAULT FALSE,
    creation_date DATE DEFAULT CURRENT_DATE,
    last_withdrawal_date DATE
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount DECIMAL(10, 2) NOT NULL,
    category_id BIGINT REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE,
    type VARCHAR(50) NOT NULL  ---(type IN ('CREDIT', 'DEBIT', 'TRANSFER'))
    description VARCHAR(255),
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interest (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    interest_rate DECIMAL(10, 2) NOT NULL,
    interest_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS interest_histories (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    interest_rate DECIMAL(10, 2) NOT NULL,
    interest_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS loans (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    loan_date DATE NOT NULL,
    status VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS loan_histories (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    loans_date DATE NOT NULL,
    status VARCHAR(255),
);


CREATE TABLE IF NOT EXISTS transfers (
    id BIGSERIAL PRIMARY KEY,
    sender_account_id BIGINT REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    receiver_account_id BIGINT REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    effective_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    bank_id BIGINT REFERENCES banks(id) ON DELETE CASCADE ON UPDATE CASCADE,
    status VARCHAR(255),
    reference VARCHAR(255) UNIQUE
);



CREATE SEQUENCE account_id_seq;

CREATE SEQUENCE transfer_id_seq;

CREATE OR REPLACE FUNCTION generate_account_number() RETURNS TRIGGER AS $$
BEGIN
    NEW.account_number := 'BIP' || LPAD(CAST(nextval('account_id_seq') AS TEXT), 11, '0');
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

SELECT t.*, a.* FROM transactions t INNER JOIN accounts a ON t.account_id = a.id WHERE t.account_id = 2 LIMIT 1;

