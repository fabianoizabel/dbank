package br.com.dbank.adapters.outbound.database;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.TransactionEntity;
import br.com.dbank.adapters.outbound.database.repositories.TransactionJpaRepository;
import br.com.dbank.domain.factory.TransactionFactory;
import br.com.dbank.domain.model.Transaction;
import br.com.dbank.domain.model.TransactionType;
import br.com.dbank.domain.repository.TransactionRepository;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

	private final TransactionJpaRepository jpaRepository;
	private final TransactionFactory factory;
	
	public TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository, TransactionFactory factory) {
		this.jpaRepository 	= jpaRepository;
		this.factory		= factory;
	}
	
	@Override
	public Transaction save(Transaction transaction) {
		TransactionEntity entity = mapToEntity(transaction);
		TransactionEntity saved = jpaRepository.saveAndFlush(entity);
		return mapToDomain(saved).get();
	}

	private Optional<Transaction> mapToDomain(TransactionEntity entity) {
		Transaction transaction = factory.build(entity.getIdempotencyKey(),
												entity.getSourceAccountID(), 
												entity.getDestinationAccountID(),
												entity.getAmount(),
												entity.getDescription(),
												TransactionType.valueOf(entity.getType()), 
												entity.getCreatedAt(), 
												entity.getUpdatedAt());
				
		return Optional.ofNullable(transaction);
	}

	private TransactionEntity mapToEntity(Transaction transaction) {
		TransactionEntity entity = new TransactionEntity();
		entity.setIdempotencyKey(transaction.getIdempotencyKey());
		entity.setSourceAccountID(transaction.getSourceAccountID());
		entity.setDestinationAccountID(transaction.getDestinationAccountID());
		entity.setAmount(transaction.getAmount());
		entity.setDescription(transaction.getDescription());
		entity.setType(transaction.getType().name()); 
		entity.setCreatedAt(OffsetDateTime.now());
		entity.setUpdatedAt(OffsetDateTime.now());
		return entity;
	}

	@Override
	public Optional<Transaction> findById(Long transactionID) {
		Optional<TransactionEntity> entity = jpaRepository.findById(transactionID);
		if (entity.isPresent())
			return mapToDomain(entity.get());
		return null;
	}

	@Override
	public Page<Transaction> findByAccountId(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Transaction> findByIdempotencyKey(String idempotencyKey) {
		Optional<TransactionEntity> entity = jpaRepository.findByIdempotencyKey(idempotencyKey);
		if (entity.isPresent())
			return mapToDomain(entity.get());
		return null;
		
	}

}
