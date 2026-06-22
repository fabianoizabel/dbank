package br.com.dbank.adapters.outbound.database;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.IdempotencyEntity;
import br.com.dbank.adapters.outbound.database.repositories.IdempotencyJpaRepository;
import br.com.dbank.domain.model.Idempotency;
import br.com.dbank.domain.repository.IdempotencyRepository;

@Repository
public class IdempotencyRepositoryAdapter implements IdempotencyRepository {

	private final IdempotencyJpaRepository repository;
	
	public IdempotencyRepositoryAdapter(IdempotencyJpaRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Idempotency save(Idempotency idempotency) {
		IdempotencyEntity entity = mapToEntity(idempotency);
		IdempotencyEntity saved = repository.saveAndFlush(entity);
		return mapToDomain(saved).get();
	}

	@Override
	public Optional<Idempotency> findByKey(String idempotencyKey) {
		Optional<IdempotencyEntity> entity = repository.findByIdempotencyKey(idempotencyKey);
		if (entity.isPresent()) {
			return mapToDomain(entity.get());
		}
		return null;
	}

	private Optional<Idempotency> mapToDomain(IdempotencyEntity entity) {
		Idempotency idempotency = Idempotency
									.builder()
									.idempotencyID(entity.getIdempotencyID())
									.idempotencyKey(entity.getIdempotencyKey())
									.responseBody(entity.getResponseBody())
									.operationType(entity.getOperationType())
									.createdAt(entity.getCreatedAt())
									.updatedAt(entity.getCreatedAt())
									.build();
		return Optional.ofNullable(idempotency);
	}

	private IdempotencyEntity mapToEntity(Idempotency idempotency) {
		IdempotencyEntity entity = new IdempotencyEntity();
		entity.setIdempotencyKey(idempotency.getIdempotencyKey());
		entity.setResponseBody(idempotency.getResponseBody());
		entity.setOperationType(idempotency.getOperationType());
		entity.setCreatedAt(OffsetDateTime.now());
		entity.setUpdatedAt(OffsetDateTime.now());
		return entity;
	}
	
}
