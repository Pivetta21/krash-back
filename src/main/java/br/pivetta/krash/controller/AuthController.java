package br.pivetta.krash.controller;

import br.pivetta.krash.dto.AuthFORM;
import br.pivetta.krash.dto.TokenDTO;
import br.pivetta.krash.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid AuthFORM authForm) {
        UsernamePasswordAuthenticationToken clientCredentials = authForm.convertAuthToken();

        try {
            Authentication authentication = authenticationManager.authenticate(clientCredentials);
            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok(new TokenDTO("Bearer", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
