package br.com.dbank.usecases.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.dbank.adapters.inbound.response.TransactionResponse;
import br.com.dbank.application.commands.CreateTransferCommand;
import br.com.dbank.application.event.DomainEventPublisher;
import br.com.dbank.application.exceptions.AccountNotFoundException;
import br.com.dbank.application.exceptions.IdempotencyNotSavedException;
import br.com.dbank.application.exceptions.InsufficientBalanceException;
import br.com.dbank.application.exceptions.InvalidAmountException;
import br.com.dbank.application.exceptions.TransactionNotFoundException;
import br.com.dbank.domain.event.TransferCompletedEvent;
import br.com.dbank.domain.factory.IdempotencyFactory;
import br.com.dbank.domain.factory.TransactionFactory;
import br.com.dbank.domain.model.Account;
import br.com.dbank.domain.model.Idempotency;
import br.com.dbank.domain.model.Transaction;
import br.com.dbank.domain.model.TransactionType;
import br.com.dbank.domain.repository.AccountRepository;
import br.com.dbank.domain.repository.IdempotencyRepository;
import br.com.dbank.domain.repository.TransactionRepository;
import br.com.dbank.infrastructure.configuration.JacksonConfig;
import br.com.dbank.usecases.CreateTransferUseCase;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CreateTransferUseCaseImpl implements CreateTransferUseCase {

	private final TransactionRepository repository;
	private final TransactionFactory factory;
	private final AccountRepository accountRepository;
	private final IdempotencyRepository idempotencyRepository;
	private final IdempotencyFactory idempotencyFactory;
	private final JacksonConfig jacksonConfig;
	private final DomainEventPublisher domainEventPublisher;
	
	public CreateTransferUseCaseImpl(TransactionRepository repository, 
										TransactionFactory factory, 
										AccountRepository accountRepository, 
										IdempotencyRepository idempotencyRepository, 
										IdempotencyFactory idempotencyFactory , 
										JacksonConfig jacksonConfig,
										DomainEventPublisher domainEventPublisher) {
        this.repository 			= repository;
        this.factory				= factory;
        this.accountRepository 		= accountRepository;
        this.idempotencyRepository	= idempotencyRepository;
        this.idempotencyFactory		= idempotencyFactory;
        this.jacksonConfig			= jacksonConfig;
        this.domainEventPublisher	= domainEventPublisher;
	}
	
	@Override
	public TransactionResponse execute(CreateTransferCommand command) throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException, TransactionNotFoundException, IdempotencyNotSavedException {
		Optional<Account> sourceAccount = accountRepository.findByIdForUpdate(command.sourceAccountId());
		if (sourceAccount == null || !sourceAccount.isPresent()) {
			throw new AccountNotFoundException("Não foi possível encontrar a conta origem. Transferência inválida.");
		}
		Optional<Account> destinationAccount = accountRepository.findByIdForUpdate(command.destinationAccountId());
		if (destinationAccount == null ||  !destinationAccount.isPresent()) {
			throw new AccountNotFoundException("Não foi possível encontrar a conta destino. Transferência inválida.");
		}

		Optional<Idempotency> idempotencySaved = idempotencyRepository.findByKey(command.idempotencyKey());
		if (idempotencySaved != null && idempotencySaved.isPresent()) {
			Optional<Transaction> transaction = repository.findByIdempotencyKey(command.idempotencyKey());
			if (!transaction.isPresent())
				throw new TransactionNotFoundException("Transação não encontrada pela chave");
			return mapToResponse(transaction.get());
		}
		
		sourceAccount.get().debit(command.amount());
		destinationAccount.get().credit(command.amount());
		
		try {
			Idempotency idempotency = idempotencyFactory.createIdempotency(command.idempotencyKey(), 
													TransactionType.TRANSFER.name(), 
													jacksonConfig.objectMapper().writeValueAsString(command));
			idempotencyRepository.save(idempotency);
		} catch (Exception e) {
			throw new IdempotencyNotSavedException(e.getLocalizedMessage());
		}
		
		accountRepository.save(sourceAccount.get());
		accountRepository.save(destinationAccount.get());
		
		Transaction newTransaction   = factory.createTransaction(command.idempotencyKey(),
													sourceAccount.get().getAccountID(),
													destinationAccount.get().getAccountID(),
													command.amount(),
													String.format("Transferencia entre contas [%s para %s]", sourceAccount.get().getNumber(), destinationAccount.get().getNumber()),
													TransactionType.TRANSFER);

		Transaction savedTransaction = repository.save(newTransaction);

		TransferCompletedEvent event = new TransferCompletedEvent(savedTransaction.getTransactionID(),
                													sourceAccount.get().getNumber(),
                													destinationAccount.get().getNumber(),
                													savedTransaction.getAmount(),
                													savedTransaction.getCreatedAt());

		domainEventPublisher.publish(event);

		TransactionResponse response	= mapToResponse(savedTransaction);
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
