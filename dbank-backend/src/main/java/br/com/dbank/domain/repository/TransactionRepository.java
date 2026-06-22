package br.com.dbank.domain.repository;

import java.util.List;
import java.util.Optional;

import br.com.dbank.domain.model.Transaction;

public interface TransactionRepository  {

	Transaction save(Transaction transaction);
	
	Optional<Transaction> findById(Long transactionID);

	Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
	
	List<Transaction> findAllByAccountId(String accountId);

}
