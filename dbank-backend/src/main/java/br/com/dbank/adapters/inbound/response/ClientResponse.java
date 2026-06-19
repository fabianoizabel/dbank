package br.com.dbank.adapters.inbound.response;

import java.time.OffsetDateTime;

public record ClientResponse(
		String clientID,
		String document,
		String name,
		String email,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt) {
}
