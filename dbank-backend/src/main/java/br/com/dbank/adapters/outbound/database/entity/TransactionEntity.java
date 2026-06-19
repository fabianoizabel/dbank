package br.com.dbank.adapters.outbound.database.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	@Column(name = "transaction_id", nullable = false)
	private Long transactionID;

	@OneToOne(fetch = FetchType.LAZY)
	private IdempotencyEntity idempotency;

	@ManyToOne(fetch = FetchType.LAZY)
	private AccountEntity account;

	@Column(name = "transaction_description", nullable = false)
	private String description;

	@Column(name = "transaction_type", nullable = false)
	private String type;

	@Column(name = "transaction_value", nullable = false)
	private BigDecimal value;

	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;

}
