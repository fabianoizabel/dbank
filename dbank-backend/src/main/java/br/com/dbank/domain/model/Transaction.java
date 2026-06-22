package br.com.dbank.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Transaction {

	Long transactionID;
	String idempotencyKey;
	String sourceAccountID;
	String destinationAccountID;
	String description;
	TransactionType type;
	BigDecimal amount;
	OffsetDateTime createdAt;
	OffsetDateTime updatedAt;

}
