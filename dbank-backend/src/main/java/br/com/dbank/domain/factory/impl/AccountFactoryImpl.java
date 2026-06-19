package br.com.dbank.domain.factory.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import br.com.dbank.domain.factory.AccountFactory;
import br.com.dbank.domain.model.Account;

@Component
public class AccountFactoryImpl implements AccountFactory {

	@Override
	public Account createInitialAccount(String clientId, String agency, Long accountNumber) {
		return Account.builder()
				.clientID(clientId)
				.agency(agency)
				.number(String.format("%05d", accountNumber))
				.beginningBalance(BigDecimal.ZERO)
				.currentBalance(BigDecimal.ZERO)
				.build();
	}

}
