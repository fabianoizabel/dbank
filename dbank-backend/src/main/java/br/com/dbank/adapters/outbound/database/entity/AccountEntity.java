package br.com.dbank.adapters.outbound.database.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

	@Id
	@Column(name = "account_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String accountID;

	@Column(name = "client_id", nullable = false)
	private String clientId;
	
	@Column(name = "account_agency", nullable = false)
	private String agency;

	@Column(name = "account_number", nullable = false)
	private String number;

	@Column(name = "beginnig_balance", nullable = false)
	private BigDecimal beginningBalance;

	@Column(name = "current_balance", nullable = false)
	private BigDecimal currentBalance;
	
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;

	@OneToMany(fetch = FetchType.LAZY)
	private List<TransactionEntity> transactions;

}
