package br.pivetta.krash.config.security;

import br.pivetta.krash.model.Client;
import br.pivetta.krash.repository.ClientRepository;
import br.pivetta.krash.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final ClientRepository clientRepository;

    public TokenAuthenticationFilter(TokenService tokenService, ClientRepository clientRepository) {
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequestHeader(httpServletRequest);
        boolean tokenValid = tokenService.isTokenValid(token);

        if (tokenValid) {
            authorizeClient(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getTokenFromRequestHeader(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }

    private void authorizeClient(String token) {
        Long clientId = tokenService.getClientIdFromToken(token);
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();

            UsernamePasswordAuthenticationToken clientCredentials = new UsernamePasswordAuthenticationToken(client, null, client.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(clientCredentials);
        }
    }
}
