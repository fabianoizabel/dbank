package br.com.dbank.usecases;

import org.springframework.stereotype.Service;

import br.com.dbank.adapters.inbound.response.TransactionResponse;
import br.com.dbank.application.commands.CreateTransferCommand;
import br.com.dbank.application.exceptions.AccountNotFoundException;
import br.com.dbank.application.exceptions.IdempotencyNotSavedException;
import br.com.dbank.application.exceptions.InsufficientBalanceException;
import br.com.dbank.application.exceptions.InvalidAmountException;
import br.com.dbank.application.exceptions.TransactionNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public interface CreateTransferUseCase {

	TransactionResponse execute(CreateTransferCommand command) throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException, TransactionNotFoundException, IdempotencyNotSavedException;

}
