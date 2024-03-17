CREATE TABLE ACCOUNTS (
  id BIGSERIAL,
  customer_firstname VARCHAR(255) NOT NULL,
  customer_lastname VARCHAR(255) DEFAULT NULL,
  birthday DATE DEFAULT NULL,
  net_monthly_salary REAL DEFAULT NULL,
  account_number BIGINT DEFAULT NULL,
  PRIMARY KEY (id)
);