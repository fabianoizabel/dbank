package br.com.dbank.adapters.outbound.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.IdempotencyEntity;

@Repository
public interface IdempotencyJpaRepository extends JpaRepository<IdempotencyEntity, Long> {

	Optional<IdempotencyEntity> findByIdempotencyKey(String idempotenceKey);

}
