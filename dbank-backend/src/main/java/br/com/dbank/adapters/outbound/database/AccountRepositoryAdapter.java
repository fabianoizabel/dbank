package br.com.dbank.adapters.outbound.database;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.AccountEntity;
import br.com.dbank.adapters.outbound.database.entity.ClientEntity;
import br.com.dbank.adapters.outbound.database.repositories.AccountJpaRepository;
import br.com.dbank.adapters.outbound.database.repositories.ClientJpaRepository;
import br.com.dbank.domain.model.Account;
import br.com.dbank.domain.repository.AccountRepository;

@Repository
public class AccountRepositoryAdapter implements AccountRepository {

	private final AccountJpaRepository jpaRepository;
	private final ClientJpaRepository clientJpaRepository;

	public AccountRepositoryAdapter(AccountJpaRepository jpaRepository, ClientJpaRepository clientJpaRepository) {
		this.jpaRepository 			= jpaRepository;
		this.clientJpaRepository	= clientJpaRepository;
	}
	
	@Override
	public Account save(Account account) {
		AccountEntity entity = mapToEntity(account);
		AccountEntity saved  = jpaRepository.saveAndFlush(entity);
		return mapToDomain(saved).get();
	}

	private Optional<Account> mapToDomain(AccountEntity entity) {
		Account account = Account
						.builder()
						.accountID(entity.getAccountID())
						.clientID(entity.getClientId())
						.agency(entity.getAgency())
						.number(entity.getNumber())
						.beginningBalance(entity.getBeginningBalance())
						.currentBalance(entity.getCurrentBalance())
						.createdAt(entity.getCreatedAt())
						.updatedAt(entity.getUpdatedAt())
						.build();
		return Optional.ofNullable(account);
	}

	private AccountEntity mapToEntity(Account account) {
		AccountEntity entity 				= new AccountEntity();
		Optional<AccountEntity> optional 	= jpaRepository.findById(account.getAccountID());
		if (optional != null && optional.isPresent())
			entity = optional.get();
		
		Optional<ClientEntity> clientEntity = clientJpaRepository.findById(account.getClientID());
		if (clientEntity == null || !clientEntity.isPresent())
			entity.setClientId(null);
		else
			entity.setClientId(clientEntity.get().getClientID());
		entity.setClientId(clientEntity.get().getClientID());
		entity.setAgency(account.getAgency());
		entity.setNumber(account.getNumber());
		entity.setBeginningBalance(account.getBeginningBalance());
		entity.setCurrentBalance(account.getCurrentBalance());
		entity.setCreatedAt(OffsetDateTime.now());
		entity.setUpdatedAt(OffsetDateTime.now());
		return entity;
	}

	@Override
	public Long nextAccountNumber() {
		return jpaRepository.getNextAccountNumber();
	}

	@Override
	public Optional<Account> findById(String accountId) {
		Optional<AccountEntity> entity = jpaRepository.findById(accountId);
		if (entity.isPresent())
			return mapToDomain(entity.get());
		return null;
	}

	@Override
	public Optional<Account> findByIdForUpdate(String accountId) {
		Optional<AccountEntity> entity = jpaRepository.findByIdForUpdate(accountId);
		if (entity.isPresent())
			return mapToDomain(entity.get());
		return null;
	}
}
