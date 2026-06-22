package br.com.dbank.domain.repository;

import java.util.Optional;

import br.com.dbank.domain.model.Idempotency;

public interface IdempotencyRepository  {

	Idempotency save(Idempotency idempotency);
	
	Optional<Idempotency> findByKey(String idempotencyID);
	
}
