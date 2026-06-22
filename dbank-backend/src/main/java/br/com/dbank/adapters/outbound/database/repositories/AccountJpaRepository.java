package br.com.dbank.adapters.outbound.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.AccountEntity;
import jakarta.persistence.LockModeType;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

	@Query(value = "select nextval('seq_account_number')", nativeQuery = true)
	Long getNextAccountNumber();

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from AccountEntity a where a.id = :accountId")
	Optional<AccountEntity> findByIdForUpdate(@Param("accountId") String accountId);
}
