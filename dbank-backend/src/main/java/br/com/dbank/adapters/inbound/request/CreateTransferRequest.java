package br.com.dbank.adapters.inbound.request;

import java.math.BigDecimal;

public class CreateTransferRequest {

	private String idempotencyKey;
	private String sourceAccountID;
	private String destinationAccountID;
	private BigDecimal amount;

	public String getIdempotencyKey() {
		return idempotencyKey;
	}
	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}
	public String getSourceAccountID() {
		return sourceAccountID;
	}
	public void setSourceAccountID(String sourceAccountID) {
		this.sourceAccountID = sourceAccountID;
	}
	public String getDestinationAccountID() {
		return destinationAccountID;
	}
	public void setDestinationAccountID(String destinationAccountID) {
		this.destinationAccountID = destinationAccountID;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}	
}
