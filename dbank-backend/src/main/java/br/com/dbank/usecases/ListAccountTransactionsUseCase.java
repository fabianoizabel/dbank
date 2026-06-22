package br.com.dbank.usecases;

import java.util.List;

import br.com.dbank.adapters.inbound.response.TransactionResponse;
import br.com.dbank.application.exceptions.AccountNotFoundException;

public interface ListAccountTransactionsUseCase {

	List<TransactionResponse> execute(String accountID) throws AccountNotFoundException;
	
}
