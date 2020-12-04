package br.pivetta.krash.repository;

import br.pivetta.krash.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findByName(String clientName, Pageable pageable);

    Optional<Client> findByEmail(String clientEmail);
}
