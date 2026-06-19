package br.com.dbank.adapters.outbound.database.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "idempotency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdempotencyEntity {

	@Id
	@Column(name = "idempotency_id", nullable = false)
	private Long idempotencyID;

	@Column(name = "idempotency_key", nullable = false)
	private String idempotencyKey;

	@Column(name = "operation_type", nullable = false)
	private String operationType;
	
	@Column(name = "request_hash", nullable = false)
	private String requestHash;

	@Column(name = "response_body", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String responseBody;	

	@Column(name = "http_status", nullable = false)
	private Integer httpStatus;
	
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;
	
}
