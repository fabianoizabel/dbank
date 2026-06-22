CREATE TABLE IF NOT EXISTS dbank.idempotency (
  	idempotency_id BIGSERIAL PRIMARY KEY,
    idempotency_key VARCHAR(100) NOT NULL,
    operation_type VARCHAR(50) NOT NULL,
    response_body JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uk_idempotency
        UNIQUE(idempotency_key)
);