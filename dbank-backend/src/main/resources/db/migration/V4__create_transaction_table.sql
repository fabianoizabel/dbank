CREATE TABLE IF NOT EXISTS dbank.transaction (
	transaction_id BIGSERIAL PRIMARY KEY,
	idempotency_key VARCHAR(100) NOT NULL,
	source_account_id character varying(36) NOT NULL,
	destination_account_id character varying(36) NOT NULL,
	transaction_description character varying(200) NOT NULL,
	transaction_type character varying(20) NOT NULL,
	transaction_value numeric(15,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);