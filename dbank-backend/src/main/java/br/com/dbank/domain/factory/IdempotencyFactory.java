package br.com.dbank.domain.factory;

import java.time.OffsetDateTime;

import br.com.dbank.domain.model.Idempotency;

public interface IdempotencyFactory {

	Idempotency createIdempotency(String idempotencyKey,
			String operationType,
			String payload);

	Idempotency build(String idempotencyKey,
						String operationType,
						String payload,
						OffsetDateTime createdAt,
						OffsetDateTime updatedAt);	
}
