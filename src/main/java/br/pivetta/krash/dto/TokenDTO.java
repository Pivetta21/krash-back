package br.pivetta.krash.dto;

public class TokenDTO {
    private String type;
    private String token;

    public TokenDTO(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}
