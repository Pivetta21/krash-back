package br.pivetta.krash.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthFORM {
    private String email;
    private String password;

    public AuthFORM() {
    }

    public UsernamePasswordAuthenticationToken convertAuthToken() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
