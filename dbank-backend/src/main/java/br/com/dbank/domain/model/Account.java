package br.com.dbank.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.dbank.application.exceptions.InsufficientBalanceException;
import br.com.dbank.application.exceptions.InvalidAmountException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Account {

	private String accountID;
	private String clientID;
	private String agency;
	private String number;
	private BigDecimal beginningBalance;
	private BigDecimal currentBalance;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;

	public void debit(BigDecimal amount) throws InsufficientBalanceException, InvalidAmountException {
		validateAmount(amount);
        if (this.currentBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente para a conta [" + this.number  + "]");
        }
        this.currentBalance = this.currentBalance.subtract(amount);
    }

    public void credit(BigDecimal amount) throws InvalidAmountException {
    	validateAmount(amount);

        this.currentBalance = this.currentBalance.add(amount);
    }

    private void validateAmount(BigDecimal amount) throws InvalidAmountException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Invalid amount.");
        }
    }	
}
