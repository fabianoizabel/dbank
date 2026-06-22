package br.com.dbank.domain.repository;

import java.util.Optional;

import br.com.dbank.domain.model.Account;

public interface AccountRepository {

	Account save(Account account);

	Long nextAccountNumber();

	Optional<Account> findById(String accountId);
	
	Optional<Account> findByIdForUpdate(String accountId);
}
