package br.com.dbank.usecases.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.dbank.adapters.inbound.response.TransactionResponse;
import br.com.dbank.application.exceptions.AccountNotFoundException;
import br.com.dbank.domain.model.Account;
import br.com.dbank.domain.model.Transaction;
import br.com.dbank.domain.repository.AccountRepository;
import br.com.dbank.domain.repository.TransactionRepository;
import br.com.dbank.usecases.ListAccountTransactionsUseCase;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ListAccountTransactionsUseCaseImpl implements ListAccountTransactionsUseCase {

	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	
	public ListAccountTransactionsUseCaseImpl(TransactionRepository repository, AccountRepository accountRepository) {
        this.transactionRepository	= repository;
        this.accountRepository		= accountRepository;
	}
	
	@Override
	public List<TransactionResponse> execute(String accountId) throws AccountNotFoundException {
		Optional<Account> sourceAccount = accountRepository.findByIdForUpdate(accountId);
		if (sourceAccount == null || !sourceAccount.isPresent()) {
			throw new AccountNotFoundException("Conta não cadastrada.");
		}
		List<Transaction> transactions 		= transactionRepository.findAllByAccountId(accountId);
		List<TransactionResponse> response 	= mapToResponseList(transactions);
		return response;
	}

	private List<TransactionResponse> mapToResponseList(List<Transaction> transactions) {
		List<TransactionResponse> response = new ArrayList<TransactionResponse>();
		if (transactions != null && transactions.size() > 0) {
			for (Transaction transaction: transactions) 
				response.add(mapToResponse(transaction));
		}
		return response;
	}

	private TransactionResponse mapToResponse(Transaction transaction) {
		TransactionResponse response = new TransactionResponse(transaction.getTransactionID(),
																transaction.getIdempotencyKey(),
																transaction.getSourceAccountID(),
																transaction.getDestinationAccountID(),
																transaction.getDescription(),
																transaction.getType().name(),
																transaction.getAmount(),
																transaction.getCreatedAt(),
																transaction.getUpdatedAt());
		return response;
	}
}
