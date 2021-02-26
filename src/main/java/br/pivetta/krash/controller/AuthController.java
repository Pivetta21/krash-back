package br.pivetta.krash.controller;

import br.pivetta.krash.dto.TokenDTO;
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
    private final ClientRepository clientRepository;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, ClientRepository clientRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/client")
    public ResponseEntity<ClientDTO> getAuthenticatedClient(@RequestHeader("Authorization") String authorizationHeader) {
        String token = tokenService.parseToken(authorizationHeader);

        if (tokenService.isTokenValid(token)) {
            Client client = this.clientRepository.getOne(tokenService.getClientIdFromToken(token));

            return ResponseEntity.ok(new ClientDTO(client));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/is-token-valid")
    public boolean isTokenValid(@RequestHeader("Authorization") String authorizationHeader) {
        String token = tokenService.parseToken(authorizationHeader);

        return tokenService.isTokenValid(token);
    }

    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody @Valid AuthFORM authForm) {
        UsernamePasswordAuthenticationToken clientCredentials = authForm.convertAuthToken();

        try {
            Authentication authentication = authenticationManager.authenticate(clientCredentials);
            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok(new TokenDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
