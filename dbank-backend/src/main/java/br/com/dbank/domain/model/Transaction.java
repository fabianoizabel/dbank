package br.com.dbank.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Transaction {

	Long transactionID;
	String idempotencyID;
	String accountID;
	String description;
	String type;
	BigDecimal value;
	OffsetDateTime createdAt;
	OffsetDateTime updatedAt;

}
