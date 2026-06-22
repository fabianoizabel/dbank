package br.com.dbank.adapters.outbound.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.TransactionEntity;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

	@Query("select t from TransactionEntity t where t.idempotencyKey = :idempotencyKey")
	Optional<TransactionEntity> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);

}
