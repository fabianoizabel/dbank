package br.com.dbank.usecase;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dbank.adapters.inbound.response.TransactionResponse;
import br.com.dbank.application.commands.CreateTransferCommand;
import br.com.dbank.application.exceptions.AccountNotFoundException;
import br.com.dbank.application.exceptions.IdempotencyNotSavedException;
import br.com.dbank.application.exceptions.InsufficientBalanceException;
import br.com.dbank.application.exceptions.InvalidAmountException;
import br.com.dbank.application.exceptions.TransactionNotFoundException;
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
import br.com.dbank.usecases.impl.CreateTransferUseCaseImpl;

@ExtendWith(MockitoExtension.class)
class CreateTransferUseCaseTest {

	@Mock
	private AccountRepository accountRepository;
	@Mock
	private TransactionRepository transactionRepository;
	@Mock
	private TransactionFactory transactionFactory;
	@Mock
	private IdempotencyRepository idempotencyRepository;
	@Mock
	private IdempotencyFactory idempotencyFactory;
	@Mock
	private JacksonConfig jacksonConfig;

	@InjectMocks
	private CreateTransferUseCaseImpl useCase;
	
	private Account sourceAccount;
	private Account destinationAccount;
	private Transaction transaction;
	private Idempotency idempotency;
	private CreateTransferCommand command;
	
	private String clientID;
	private String sourceAccountID;
	private String destinationAccountID 	= UUID.randomUUID().toString();

	@BeforeEach
	void setup() throws JsonProcessingException {
		when(jacksonConfig.objectMapper()).thenReturn(new ObjectMapper());
		
		clientID 				= UUID.randomUUID().toString();
		sourceAccountID 		= UUID.randomUUID().toString();
		destinationAccountID	= UUID.randomUUID().toString();
		
		sourceAccount = Account.builder()
            .accountID(sourceAccountID)
            .clientID(clientID)
            .currentBalance(BigDecimal.valueOf(1000))
            .build();

		destinationAccount = Account.builder()
            .accountID(sourceAccountID)
            .clientID(clientID)
            .currentBalance(BigDecimal.valueOf(500))
            .build();

		command = new CreateTransferCommand(
				"KEY-123",
				sourceAccountID,
				destinationAccountID,
                BigDecimal.valueOf(200));
		
		idempotency = Idempotency.builder()
				.idempotencyID(10L)				
				.idempotencyKey(command.idempotencyKey())
				.operationType("TRANSFER")
				.responseBody(jacksonConfig.objectMapper().writeValueAsString(command))
				.build();

		transaction = Transaction.builder()
			.transactionID(10L)
			.idempotencyKey(command.idempotencyKey())
			.sourceAccountID(sourceAccount.getAccountID())
			.destinationAccountID(destinationAccount.getAccountID())
			.amount(command.amount())
			.description(String.format("Transferencia entre contas [%s para %s]", sourceAccount.getNumber(), destinationAccount.getNumber()))
			.type(TransactionType.TRANSFER)
			.build();
			
	}

	@Test
	@DisplayName("Should successfully transfer")
	void shouldTransferSuccessfully() throws JsonProcessingException {
	
	    when(accountRepository.findByIdForUpdate(sourceAccountID))
	            .thenReturn(Optional.of(sourceAccount));
	
	    when(accountRepository.findByIdForUpdate(destinationAccountID))
	            .thenReturn(Optional.of(destinationAccount));
	
	    when(jacksonConfig.objectMapper()).thenReturn(new ObjectMapper());
	    
	    when(idempotencyFactory.createIdempotency(
	    		command.idempotencyKey(), 
				TransactionType.TRANSFER.name(), 
				jacksonConfig.objectMapper().writeValueAsString(command)))
	    		.thenReturn(idempotency);	    
	    
	    when(transactionFactory.createTransaction(command.idempotencyKey(),
										sourceAccount.getAccountID(),
										destinationAccount.getAccountID(),
										command.amount(),
										String.format("Transferencia entre contas [%s para %s]", sourceAccount.getNumber(), destinationAccount.getNumber()),
										TransactionType.TRANSFER))
	    			.thenReturn(transaction);

	    when(transactionRepository.save(transaction)).thenReturn(transaction);
	    
	    TransactionResponse response = null;
		try {
			response = useCase.execute(command);
		} catch (AccountNotFoundException e) {
			fail(e.getMessage());
		} catch (InvalidAmountException e) {
			fail(e.getMessage());
		} catch (InsufficientBalanceException e) {
			fail(e.getMessage());
		} catch (TransactionNotFoundException e) {
			fail(e.getMessage());
		} catch (IdempotencyNotSavedException e) {
			fail(e.getMessage());
		}
	
	    verify(accountRepository).findByIdForUpdate(sourceAccount.getAccountID());
	    verify(accountRepository).findByIdForUpdate(destinationAccount.getAccountID());
	    verify(idempotencyRepository).findByKey(command.idempotencyKey());
	    verify(idempotencyRepository).save(idempotency);
	    verify(accountRepository).save(sourceAccount);
	    verify(accountRepository).save(destinationAccount);
	    verify(transactionRepository).save(transaction);
	    
	    assertNotNull(response);
	}

}