CREATE TABLE IF NOT EXISTS dbank.client (
	client_id character varying(100) NOT NULL PRIMARY KEY,
	client_document character varying(50) NOT NULL,
	client_password character varying(150) NOT NULL,
	client_name character varying(250) NOT NULL,
	client_email character varying(250) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);