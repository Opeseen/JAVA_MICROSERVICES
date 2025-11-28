CREATE TABLE IF NOT EXISTS account_transactions (
  transaction_id INT AUTO_INCREMENT PRIMARY KEY,
  account_number int NOT NULL,
  customer_id int NOT NULL,
  transaction_dt date NOT NULL,
  transaction_summary varchar(200) NOT NULL,
  transaction_type varchar(50) NOT NULL,
  transaction_amt int NOT NULL,
  closing_balance int NOT NULL,
  created_at date DEFAULT NULL
);
