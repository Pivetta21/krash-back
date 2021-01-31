package br.pivetta.krash.dto;

public class AuthDTO {
    private ClientDTO client;
    private String token;

    public AuthDTO(ClientDTO client, String token) {
        this.client = client;
        this.token = token;
    }

    public ClientDTO getClient() {
        return client;
    }

    public String getToken() {
        return token;
    }
}
