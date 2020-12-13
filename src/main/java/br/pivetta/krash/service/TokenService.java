package br.pivetta.krash.service;

import br.pivetta.krash.model.Client;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${krash.jwt.expiration}")
    private Long expiration;
    @Value("${krash.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        Client client = (Client) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + expiration);

        return Jwts.builder()
                .setIssuer("API Krash")
                .setSubject(client.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
