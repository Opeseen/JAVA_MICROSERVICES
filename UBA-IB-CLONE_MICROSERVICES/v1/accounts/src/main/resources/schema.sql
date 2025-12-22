CREATE TABLE IF NOT EXISTS customers (
	customer_id INT AUTO_INCREMENT PRIMARY KEY,
	email VARCHAR(50) NOT NULL UNIQUE,
	bvn INT NOT NULL UNIQUE,
	full_name VARCHAR(50) NOT NULL,
	phone VARCHAR(20) NOT NULL,
	branch_address VARCHAR(255) NOT NULL,
	account_type VARCHAR(50) NOT NULL,
	created_at DATE NOT NULL,
	created_by VARCHAR(50) NOT NULL,
	updated_at DATE DEFAULT NULL,
	updated_by VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
	account_number INT PRIMARY KEY,
	customer_id INT NOT NULL,
	balance INT DEFAULT 0,
	created_at DATE NOT NULL,
	created_by VARCHAR(30) NOT NULL,
	updated_at DATE DEFAULT NULL,
	updated_by VARCHAR(30) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS account_transactions (
  transaction_id INT AUTO_INCREMENT PRIMARY KEY,
  account_number int NOT NULL,
  customer_id int NOT NULL,
  transaction_dt datetime NOT NULL,
  transaction_summary varchar(255) NOT NULL,
  transaction_type varchar(50) NOT NULL,
  transaction_amt int NOT NULL,
  closing_balance int NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_account_txn_date
ON account_transactions(account_number, transaction_dt DESC);
