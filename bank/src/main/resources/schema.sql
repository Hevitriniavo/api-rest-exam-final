DROP DATABASE IF EXISTS numeric_bank;
CREATE DATABASE numeric_bank;

\c numeric_bank;

CREATE TABLE IF NOT EXISTS banks (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('ENTREE', 'SORTIE'))
);

CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    birthday DATE NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    net_Monthly_salary REAL DEFAULT NULL,
    account_number VARCHAR(14) UNIQUE,
    overdraft_limit DECIMAL(10, 2) ,
    overdraft_enabled BOOLEAN DEFAULT FALSE,
    creation_date DATE DEFAULT CURRENT_DATE,
    last_withdrawal_date DATE,
    bank_id BIGINT NOT NULL REFERENCES banks(id)
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount DECIMAL(10, 2) NOT NULL,
    reason VARCHAR(255),
    effective_date DATE NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    comment VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transactions_histories (
    id BIGSERIAL PRIMARY KEY,
    transaction_id BIGINT REFERENCES transactions(id),
    operation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transfers (
    id BIGSERIAL PRIMARY KEY,
    sender_account_id BIGINT REFERENCES accounts(id),
    receiver_account_id BIGINT REFERENCES accounts(id),
    amount DECIMAL(10, 2) NOT NULL,
    reason VARCHAR(255),
    effective_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(255) DEFAULT 'PENDING',
    reference VARCHAR(255) UNIQUE NOT NULL,
    cancelled_date TIMESTAMP
);


CREATE TABLE IF NOT EXISTS interests (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT REFERENCES accounts(id),
    amount DECIMAL(10, 2) NOT NULL,
    interest_rate DECIMAL(10, 2) NOT NULL,
    interest_date DATE NOT NULL
);

CREATE SEQUENCE account_id_seq;
CREATE SEQUENCE transfer_id_seq;


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


CREATE OR REPLACE FUNCTION generate_transfer_reference() RETURNS TRIGGER AS $$
BEGIN
    NEW.reference := 'TRX' || to_char(current_date, 'YYYYMMDD') || lpad(nextval('transfer_id_seq'), 18, '0');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER transfer_reference_trigger
    BEFORE INSERT ON transfers
    FOR EACH ROW
    EXECUTE FUNCTION generate_transfer_reference();


CREATE OR REPLACE FUNCTION check_scheduled_transfers() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.effective_date <= CURRENT_TIMESTAMP THEN
        NEW.status := 'COMPLETED';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_scheduled_transfers_trigger
    BEFORE INSERT ON transfers
    FOR EACH ROW
    EXECUTE FUNCTION check_scheduled_transfers();
