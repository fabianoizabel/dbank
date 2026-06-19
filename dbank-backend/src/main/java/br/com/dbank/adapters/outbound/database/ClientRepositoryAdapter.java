package br.com.dbank.adapters.outbound.database;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.ClientEntity;
import br.com.dbank.adapters.outbound.database.repositories.ClientJpaRepository;
import br.com.dbank.domain.model.Client;
import br.com.dbank.domain.repository.ClientRepository;

@Repository
public class ClientRepositoryAdapter implements ClientRepository {

	private final ClientJpaRepository jpaRepository;
	
	public ClientRepositoryAdapter(ClientJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
	@Override
	public Client save(Client client) {
		ClientEntity entity = mapToEntity(client);
		ClientEntity saved = jpaRepository.save(entity);
		return mapToDomain(saved).get();
	}

	private Optional<Client> mapToDomain(ClientEntity entity) {
		Client client = Client
						.builder()
						.clientID(entity.getClientID())
						.name(entity.getName())
						.email(entity.getEmail())
						.document(entity.getDocument())
						.createdAt(entity.getCreatedAt())
						.updatedAt(entity.getUpdatedAt())
						.build();
		return Optional.ofNullable(client);
	}

	private ClientEntity mapToEntity(Client client) {
		ClientEntity entity = new ClientEntity();
		entity.setName(client.getName());
		entity.setEmail(client.getEmail());
		entity.setDocument(client.getDocument());
		entity.setPassword(client.getPassword());
		entity.setCreatedAt(OffsetDateTime.now());
		entity.setUpdatedAt(OffsetDateTime.now());
		return entity;
	}

	@Override
	public Optional<Client> findById(String clientID) {
		Optional<ClientEntity> entity = jpaRepository.findById(clientID);
		if (entity.isPresent())
			return mapToDomain(entity.get());
		return null;
	}

	@Override
	public Optional<Client> findByDocument(String document) {
		Optional<ClientEntity> entity = jpaRepository.findByDocument(document);
		if (entity.isPresent())
			return mapToDomain(entity.get());
		return null;
	}

}
