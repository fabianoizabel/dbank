package br.com.dbank.domain.factory.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import br.com.dbank.domain.factory.TransactionFactory;
import br.com.dbank.domain.model.Transaction;
import br.com.dbank.domain.model.TransactionType;

@Component
public class TransactionFactoryImpl implements TransactionFactory {

	@Override
	public Transaction createTransaction(String idempotencyID, String sourceAccountId, String destinationAccountId,
			BigDecimal amount, String description, TransactionType type) {
		return Transaction.builder()
				.idempotencyKey(idempotencyID)
				.sourceAccountID(sourceAccountId)
				.destinationAccountID(destinationAccountId)
				.amount(amount)
				.description(description)
				.type(type)
				.build();
	}

	@Override
	public Transaction build(Long transactionID, String idempotencyID, String sourceAccountId, String destinationAccountId,
			BigDecimal amount, String description, TransactionType type, OffsetDateTime createdAt,
			OffsetDateTime updatedAt) {
		return Transaction.builder()
				.transactionID(transactionID)
				.idempotencyKey(idempotencyID)
				.sourceAccountID(sourceAccountId)
				.destinationAccountID(destinationAccountId)
				.amount(amount)
				.description(description)
				.type(type)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.build();
	}

}
