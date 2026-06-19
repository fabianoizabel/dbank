package br.com.dbank.domain.repository;

import java.util.Optional;

import br.com.dbank.domain.model.Client;

public interface ClientRepository  {

	Client save(Client client);
	
	Optional<Client> findById(String clientID);

	Optional<Client> findByDocument(String document);
	
}
