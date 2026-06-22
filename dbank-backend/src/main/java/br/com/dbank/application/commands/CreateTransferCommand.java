package br.com.dbank.application.commands;

import java.math.BigDecimal;

public record CreateTransferCommand(
		String idempotencyKey,
		String sourceAccountId,
		String destinationAccountId,
		BigDecimal amount) {
}
