package br.pivetta.krash.controller;

import br.pivetta.krash.dto.AuthDTO;
import br.pivetta.krash.dto.AuthFORM;
import br.pivetta.krash.dto.ClientDTO;
import br.pivetta.krash.model.Client;
import br.pivetta.krash.repository.ClientRepository;
import br.pivetta.krash.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ClientRepository clientRepository;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, ClientRepository clientRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public ResponseEntity<AuthDTO> auth(@RequestBody @Valid AuthFORM authForm) {
        UsernamePasswordAuthenticationToken clientCredentials = authForm.convertAuthToken();

        try {
            Authentication authentication = authenticationManager.authenticate(clientCredentials);
            String token = tokenService.generateToken(authentication);

            Client client = this.clientRepository.getOne(tokenService.getClientIdFromToken(token));
            AuthDTO authenticatedClient = new AuthDTO(new ClientDTO(client), token);

            return ResponseEntity.ok(authenticatedClient);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
