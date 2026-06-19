package br.com.dbank.domain.factory;

import br.com.dbank.domain.model.Account;

public interface AccountFactory {

	Account createInitialAccount(String clientId, String agency, Long accountNumber);
	
}
