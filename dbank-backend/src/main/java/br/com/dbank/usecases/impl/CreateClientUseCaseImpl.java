package br.com.dbank.usecases.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dbank.adapters.inbound.response.ClientResponse;
import br.com.dbank.application.commands.CreateClientCommand;
import br.com.dbank.application.exceptions.ClientDocumentAlreadyExistsException;
import br.com.dbank.domain.factory.AccountFactory;
import br.com.dbank.domain.model.Account;
import br.com.dbank.domain.model.Client;
import br.com.dbank.domain.repository.AccountRepository;
import br.com.dbank.domain.repository.ClientRepository;
import br.com.dbank.usecases.CreateClientUseCase;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CreateClientUseCaseImpl implements CreateClientUseCase {

	@Value("${dbank.business.default.agency}")
	private String defaultAgency;
	
	private final ClientRepository repository;
	private final AccountRepository accountRepository;
	private final AccountFactory accountFactory;

	public CreateClientUseCaseImpl(ClientRepository repository, AccountRepository accountRepository, AccountFactory accountFactory) {
        this.repository 		= repository;
        this.accountRepository 	= accountRepository;
        this.accountFactory		= accountFactory;
    }
	
	@Override
	public ClientResponse execute(CreateClientCommand command) throws ClientDocumentAlreadyExistsException {
		Optional<Client> client = repository.findByDocument(command.document());
		if (client != null && client.isPresent()) {
			throw new ClientDocumentAlreadyExistsException("Não foi possível criar o cliente: documento já cadastrado.");
		}
		Client newClient 	= mapToClient(command);
		Client savedClient 	= repository.save(newClient);
		

		Account newAccount	= accountFactory.createInitialAccount(savedClient.getClientID(), 
																  this.defaultAgency, 
																  accountRepository.nextAccountNumber());
		accountRepository.save(newAccount);
		
		ClientResponse response	= mapToResponse(savedClient);
		return response;
	}

	private ClientResponse mapToResponse(Client savedClient) {
		ClientResponse response = new ClientResponse(savedClient.getClientID(),
													 savedClient.getDocument(),
													 savedClient.getName(), 
													 savedClient.getEmail(), 
													 savedClient.getCreatedAt(), 
													 savedClient.getUpdatedAt());
		return response;
	}

	private Client mapToClient(CreateClientCommand command) {
		Client client = Client
							.builder()
							.document(command.document())
							.name(command.name())
							.email(command.email())
							.password(new BCryptPasswordEncoder().encode(command.password()))
							.build();
		return client;
	}

}
