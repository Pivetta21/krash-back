package br.pivetta.krash.controller;

import br.pivetta.krash.dto.ClientDTO;
import br.pivetta.krash.dto.ClientFORM;
import br.pivetta.krash.dto.ClientUpdateFORM;
import br.pivetta.krash.model.Client;
import br.pivetta.krash.model.Permission;
import br.pivetta.krash.repository.ClientRepository;
import br.pivetta.krash.repository.PermissionRepository;
import br.pivetta.krash.service.TokenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientRepository clientRepository;
    private final PermissionRepository permissionRepository;
    private final TokenService tokenService;

    public ClientController(
            ClientRepository clientRepository, PermissionRepository permissionRepository, TokenService tokenService
    ) {
        this.clientRepository = clientRepository;
        this.permissionRepository = permissionRepository;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Page<ClientDTO> showClients(@RequestParam(required = false) String clientName, Pageable pageable) {
        Page<Client> clients;

        if (clientName != null) {
            clients = clientRepository.findByName(clientName, pageable);
        } else {
            clients = clientRepository.findAll(pageable);
        }

        return ClientDTO.convertPage(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            ClientDTO client = new ClientDTO(optionalClient.get());

            return ResponseEntity.ok(client);
        }

        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientFORM clientFORM, UriComponentsBuilder uriBuilder) {
        Optional<Permission> permissionOptional = permissionRepository.findByName("USER");

        if (permissionOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Client client = new Client(clientFORM.getEmail(), clientFORM.getName());
        client.setPicture(clientFORM.getPicture());
        client.setPassword(new BCryptPasswordEncoder().encode(clientFORM.getPassword()));
        client.setSignUpDate(LocalDateTime.now());
        client.setPermission(permissionOptional.get());

        clientRepository.save(client);

        URI uri = uriBuilder.path("/client/{id}").buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(uri).body(new ClientDTO(client));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientUpdateFORM clientUpdateFORM,
                                                  @RequestHeader("Authorization") String authorizationHeader
    ) {
        Optional<Client> clientOptional = clientRepository.findByEmail(clientUpdateFORM.getEmail());

        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String token = tokenService.parseToken(authorizationHeader);

        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.badRequest().build();
        }

        Client clientAuthenticated = this.clientRepository.getOne(tokenService.getClientIdFromToken(token));

        if (!clientAuthenticated.getEmail().equals(clientUpdateFORM.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Client client = clientUpdateFORM.updateClient(clientOptional.get());

        return ResponseEntity.ok(new ClientDTO(client));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClientById(@PathVariable Long id,
                                                      @RequestBody ClientUpdateFORM clientUpdateFORM
    ) {
        Optional<Client> clientOptional = clientRepository.findById(id);

        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Client client = clientUpdateFORM.updateClient(clientOptional.get());

        return ResponseEntity.ok(new ClientDTO(client));
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);

        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        clientRepository.delete(clientOptional.get());

        return ResponseEntity.ok().build();
    }
}
