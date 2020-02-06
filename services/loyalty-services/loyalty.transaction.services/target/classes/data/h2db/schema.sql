DROP TABLE IF EXISTS LOYALTY_CUSTOMER;

CREATE TABLE LOYALTY_CUSTOMER (
	id BIGINT AUTO_INCREMENT  PRIMARY KEY,
	user_id VARCHAR(25) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NULL,
	balance BIGINT NOT NULL,
	custom_message VARCHAR(100) NULL 
);



DROP TABLE IF EXISTS LOYALTY_TRANSACTION;

CREATE TABLE LOYALTY_TRANSACTION (
	id BIGINT AUTO_INCREMENT  PRIMARY KEY,
	customer_id BIGINT NOT NULL,
	transaction_type INTEGER not NULL,
	transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	transaction_amount numeric(19,2) NOT NULL,
	loyalty_points BIGINT NOT NULL,
	status INTEGER not NULL,
	source VARCHAR(500) NULL,
	transaction_remarks VARCHAR(100) NULL,
	failure_reason VARCHAR(500) NULL
);