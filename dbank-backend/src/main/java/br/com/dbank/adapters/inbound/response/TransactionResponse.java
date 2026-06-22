package br.com.dbank.adapters.inbound.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionResponse(
		Long transactionID,
		String idempotencyID,
		String sourceAccountID,
		String destinationAccountID,
		String description,
		String type,
		BigDecimal amount,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt) {
}
