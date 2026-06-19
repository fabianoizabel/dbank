package br.com.dbank.domain.repository;

import br.com.dbank.domain.model.Account;

public interface AccountRepository {

	Account save(Account account);

	Long nextAccountNumber();

}
