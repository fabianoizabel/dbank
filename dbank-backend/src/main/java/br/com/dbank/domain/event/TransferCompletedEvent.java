package br.com.dbank.domain.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransferCompletedEvent(
        Long transactionId,
        String sourceAccountId,
        String destinationAccountId,
        BigDecimal amount,
        OffsetDateTime occurredAt
) {
}