package br.com.dbank.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.dbank.domain.model.Transaction;

public interface TransactionRepository  {

	Transaction save(Transaction transaction);
	
	Optional<Transaction> findById(Long transactionID);

	Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
	
	Page<Transaction> findByAccountId(String accountId);

}
