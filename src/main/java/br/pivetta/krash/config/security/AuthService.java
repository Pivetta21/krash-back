package br.pivetta.krash.config.security;

import br.pivetta.krash.model.Client;
import br.pivetta.krash.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private final ClientRepository clientRepository;

    public AuthService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> clientOptional = clientRepository.findByEmail(username);

        if (clientOptional.isPresent()) {
            return clientOptional.get();
        }

        throw new UsernameNotFoundException("Dados inválidos.");
    }
}
