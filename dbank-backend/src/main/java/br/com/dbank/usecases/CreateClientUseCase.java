package br.com.dbank.usecases;

import br.com.dbank.adapters.inbound.response.ClientResponse;
import br.com.dbank.application.commands.CreateClientCommand;
import br.com.dbank.application.exceptions.ClientDocumentAlreadyExistsException;

public interface CreateClientUseCase {

	ClientResponse execute(CreateClientCommand command) throws ClientDocumentAlreadyExistsException;

}
