package br.com.dbank.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Account {

	String accountID;
	String clientID;
	String agency;
	String number;
	BigDecimal beginningBalance;
	BigDecimal currentBalance;
	OffsetDateTime createdAt;
	OffsetDateTime updatedAt;

}
