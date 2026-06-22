package br.com.dbank.domain.factory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.dbank.domain.model.Transaction;
import br.com.dbank.domain.model.TransactionType;

public interface TransactionFactory {

	Transaction createTransaction(String idempotencyID, 
			String sourceAccountId, 
			String destinationAccountId, 
			BigDecimal amount, 
			String description,	
			TransactionType type);

	Transaction build(String idempotencyID, 
						String sourceAccountId, 
						String destinationAccountId, 
						BigDecimal amount, 
						String description,	
						TransactionType type,
						OffsetDateTime createdAt,
						OffsetDateTime updatedAt);	
}
