package br.com.dbank.adapters.outbound.database.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "transaction_id", nullable = false)
	private Long transactionID;

	@Column(name = "idempotency_key", nullable = false)
	private String idempotencyKey;

	@Column(name = "source_account_id", nullable = false)
	private String sourceAccountID;

	@Column(name = "destination_account_id", nullable = false)
	private String destinationAccountID;

	@Column(name = "transaction_description", nullable = false)
	private String description;

	@Column(name = "transaction_type", nullable = false)
	private String type;

	@Column(name = "transaction_value", nullable = false)
	private BigDecimal amount;

	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;

}
