package br.com.dbank.domain.model;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Idempotency {

	Long idempotencyID;
	String idempotencyKey;
	String operationType;
	String requestHash;
	String responseBody;	
	Integer httpStatus;
	OffsetDateTime createdAt;

}
