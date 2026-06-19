package br.com.dbank.adapters.outbound.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dbank.adapters.outbound.database.entity.ClientEntity;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, String> {

	Optional<ClientEntity> findByDocument(String username);

}
