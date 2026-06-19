package br.com.dbank.adapters.outbound.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.AccountEntity;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

	@Query(value = "SELECT nextval('seq_account_number')", nativeQuery = true)
	Long getNextAccountNumber();

}
