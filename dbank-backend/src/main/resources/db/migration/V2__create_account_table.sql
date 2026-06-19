CREATE TABLE IF NOT EXISTS dbank.account (
	account_id character varying(100) NOT NULL PRIMARY KEY,
	client_id character varying(100) NOT NULL REFERENCES dbank.client(client_id),
	account_agency character varying(10) NOT NULL,
	account_number character varying(10) NOT NULL,
	beginnig_balance numeric(15,2),
	current_balance numeric(15,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);