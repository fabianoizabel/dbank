package br.com.dbank.domain.factory.impl;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import br.com.dbank.domain.factory.IdempotencyFactory;
import br.com.dbank.domain.model.Idempotency;

@Component
public class IdempotencyFactoryImpl implements IdempotencyFactory {

	@Override
	public Idempotency createIdempotency(String idempotencyKey, String operationType, String payload) {
		return Idempotency.builder()
				.idempotencyKey(idempotencyKey)
				.operationType(operationType)
				.responseBody(payload)
				.build();
	}

	@Override
	public Idempotency build(String idempotencyKey, String operationType, String payload, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
		return Idempotency.builder()
				.idempotencyKey(idempotencyKey)
				.operationType(operationType)
				.responseBody(payload)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.build();
	}

}
